package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.request.ProductRequest;
import ru.randomplay.musicshop.dto.response.ProductResponse;
import ru.randomplay.musicshop.entity.Category;
import ru.randomplay.musicshop.entity.Product;
import ru.randomplay.musicshop.entity.ProductCategoryLink;
import ru.randomplay.musicshop.entity.Supplier;
import ru.randomplay.musicshop.mapper.ProductMapper;
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
    public List<ProductResponse> getAll() {
        return productMapper.toProductResponseList(productRepository.findAllWithCategories());
    }

    @Override
    @Transactional
    public void save(ProductRequest productRequest) {
        if (productRepository.findByName(productRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Product with this name already exists");
        }

        // Находим поставщика по его id
        Supplier supplier = supplierRepository
                .findById(productRequest.getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid supplier id"));

        // Получаем список категорий по их id
        List<Category> categoryList = new ArrayList<>(
                categoryRepository.findAllById(productRequest.getCategoryIds())
        );
        if (categoryList.size() != productRequest.getCategoryIds().size()) {
            throw new IllegalArgumentException("Invalid categories id");
        }

        Product createdProduct = productMapper.toProduct(productRequest, supplier);

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
}
