package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.dto.response.ProductResponse;
import ru.randomplay.musicshop.dto.update.ProductUpdateRequest;
import ru.randomplay.musicshop.entity.Category;
import ru.randomplay.musicshop.entity.Product;
import ru.randomplay.musicshop.entity.ProductCategoryLink;
import ru.randomplay.musicshop.entity.Supplier;
import ru.randomplay.musicshop.mapper.ProductMapper;
import ru.randomplay.musicshop.model.ProductStatus;
import ru.randomplay.musicshop.repository.CategoryRepository;
import ru.randomplay.musicshop.repository.ProductRepository;
import ru.randomplay.musicshop.repository.SupplierRepository;
import ru.randomplay.musicshop.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Value("${app.image.upload-dir}")
    private String uploadDir;

    @Override
    public ProductResponse get(Long id) {
        return productMapper.toProductResponse(productRepository.findByIdWithCategoriesAndSupplier(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with ID " + id + " doesn't exist")));
    }

    @Override
    public List<ProductResponse> getAll() {
        return productMapper.toProductResponseList(productRepository.findAllWithCategoriesAndSupplier());
    }

    @Override
    public List<ProductResponse> getAllByStatus(ProductStatus status) {
        return productMapper.toProductResponseList(productRepository.findAllWithCategoriesAndSupplierByStatus(status));
    }

    private String createFilename(MultipartFile image) {
        String filename = null;
        if (!image.isEmpty()) {
            // проверка, чтобы файл имел необходимый формат (картинка)
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            // получение оригинального имени
            String originalName = Optional.ofNullable(image.getOriginalFilename())
                    .map(StringUtils::cleanPath)
                    .filter(name -> !name.isEmpty())
                    .orElse("image");

            // извлечение и валидация расширения
            String extension = Stream.of(".jpg", ".jpeg", ".png")
                    .filter(ext -> originalName.toLowerCase().endsWith(ext))
                    .findFirst()
                    .orElse(".jpg");

            filename = UUID.randomUUID() + extension;

            try {
                image.transferTo(new File(uploadDir + File.separator + filename));
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image", e);
            }
        }
        return filename;
    }

    private void deleteOldImage(String oldImageFilename) {
        if (oldImageFilename != null && !oldImageFilename.isEmpty()) {
            try {
                Path oldImagePath = Paths.get(uploadDir, oldImageFilename);
                if (Files.exists(oldImagePath)) {
                    Files.delete(oldImagePath);
                }
            } catch (IOException e) {
                throw new RuntimeException("Failed to delete previous image", e);
            }
        }
    }

    @Override
    @Transactional
    public void save(ProductCreateRequest productCreateRequest, MultipartFile image) {
        if (productRepository.findByName(productCreateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Product with name " + productCreateRequest.getName() + " already exists");
        }

        // Получаем список категорий по их id
        List<Category> categoryList = new ArrayList<>(
                categoryRepository.findAllById(productCreateRequest.getCategoryIds())
        );

        // Ошибка возникает, если были переданы неизвестные ID категорий, которые не смогли быть получены
        if (categoryList.size() != productCreateRequest.getCategoryIds().size()) {
            throw new IllegalArgumentException("Invalid categories ID");
        }

        // Находим поставщика по его id
        Supplier supplier = supplierRepository.findById(productCreateRequest.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + productCreateRequest.getSupplierId() + " doesn't exist"));
        productCreateRequest.setImageFilename(createFilename(image));
        Product createdProduct = productMapper.toProduct(productCreateRequest, supplier);

        // Прописываем связи между Product и Category
        for (Category category : categoryList) {
            ProductCategoryLink link = new ProductCategoryLink();
            link.setProduct(createdProduct);
            link.setCategory(category);
            createdProduct.getCategoryLinks().add(link);
            category.getProductLinks().add(link);
        }

        productRepository.save(createdProduct);
    }

    @Transactional
    @Override
    public void update(Long id, ProductUpdateRequest productUpdateRequest, MultipartFile image) {
        Product product = productRepository.findByIdWithCategories(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " doesn't exist"));

        // Проверяем уникальность имени, если оно изменилось
        if (!product.getName().equals(productUpdateRequest.getName()) &&
                productRepository.findByName(productUpdateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Product with name " + productUpdateRequest.getName() + " already exists");
        }

        String newImageFilename = createFilename(image);
        if (newImageFilename != null) {
            deleteOldImage(product.getImageFilename());
            product.setImageFilename(newImageFilename);
        }

        productMapper.updateProduct(product, productUpdateRequest);

        // Получаем ID новых категорий
        List<Long> newCategoryIds = productUpdateRequest.getCategoryIds() != null
                ? productUpdateRequest.getCategoryIds()
                : new ArrayList<>();

        // Получаем ID текущих категорий продукта
        List<Long> currentCategoryIds = product.getCategoryLinks().stream()
                .map(link -> link.getCategory().getId())
                .toList();

        // Получаем ID категорий для удаления (есть в текущих, но нет в новых)
        List<Long> categoryIdsToRemove = currentCategoryIds.stream()
                .filter(categoryId -> !newCategoryIds.contains(categoryId))
                .toList();

        // Получаем ID категорий для добавления (есть в новых, но нет в текущих)
        List<Long> categoryIdsToAdd = newCategoryIds.stream()
                .filter(categoryId -> !currentCategoryIds.contains(categoryId))
                .toList();

        // Удаляем пропавшие категории
        if (!categoryIdsToRemove.isEmpty()) {
            List<ProductCategoryLink> linksToRemove = product.getCategoryLinks().stream()
                    .filter(link -> categoryIdsToRemove.contains(link.getCategory().getId()))
                    .toList();

            for (ProductCategoryLink link : linksToRemove) {
                link.getCategory().getProductLinks().remove(link);
                product.getCategoryLinks().remove(link);
            }
        }

        // Добавляем новые категории
        if (!categoryIdsToAdd.isEmpty()) {
            List<Category> categoriesToAdd = categoryRepository.findAllById(categoryIdsToAdd);
            if (categoriesToAdd.size() != categoryIdsToAdd.size()) {
                throw new IllegalArgumentException("Invalid categories ID");
            }

            for (Category category : categoriesToAdd) {
                ProductCategoryLink link = new ProductCategoryLink();
                link.setProduct(product);
                link.setCategory(category);
                product.getCategoryLinks().add(link);
                category.getProductLinks().add(link);
            }
        }

        productRepository.save(product);
    }
}
