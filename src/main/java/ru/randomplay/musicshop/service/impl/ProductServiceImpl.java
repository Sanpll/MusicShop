package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductResponse get(Long id) {
        return productMapper.toProductResponse(productRepository.findById(id)
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

    @Override
    @Transactional
    public void save(ProductCreateRequest productCreateRequest) {
        if (productRepository.findByName(productCreateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Product with name " + productCreateRequest.getName() + " already exists");
        }

        // Находим поставщика по его id
        Supplier supplier = supplierRepository.findById(productCreateRequest.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Supplier with ID " + productCreateRequest.getSupplierId() + " doesn't exist"));

        // Получаем список категорий по их id
        List<Category> categoryList = new ArrayList<>(
                categoryRepository.findAllById(productCreateRequest.getCategoryIds())
        );
        if (categoryList.size() != productCreateRequest.getCategoryIds().size()) {
            throw new IllegalArgumentException("Invalid categories ID");
        }

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
    public void update(Long id, ProductUpdateRequest productUpdateRequest) {
        Product product = productRepository.findByIdWithCategories(id)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + id + " doesn't exist"));

        // Проверяем уникальность имени, если оно изменилось
        if (!product.getName().equals(productUpdateRequest.getName()) &&
                productRepository.findByName(productUpdateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Product with name " + productUpdateRequest.getName() + " already exists");
        }

        if (productUpdateRequest.getImageFilename() != null) {
            product.setImageFilename(productUpdateRequest.getImageFilename());
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
