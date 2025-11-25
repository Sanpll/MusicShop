package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.entity.Category;
import ru.randomplay.musicshop.entity.Product;
import ru.randomplay.musicshop.entity.Supplier;
import ru.randomplay.musicshop.mapper.ProductMapper;
import ru.randomplay.musicshop.repository.CategoryRepository;
import ru.randomplay.musicshop.repository.ProductRepository;
import ru.randomplay.musicshop.repository.SupplierRepository;
import ru.randomplay.musicshop.service.ProductService;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final SupplierRepository supplierRepository;
    private final ProductMapper productMapper;

    @Override
    public void save(ProductCreateRequest productCreateRequest) {
        if (productRepository.findByName(productCreateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Product with this name already exists");
        }

        Supplier supplier = supplierRepository
                .findById(productCreateRequest
                        .getSupplierId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid supplier id"));

        Set<Category> categorySet = new HashSet<>(
                categoryRepository.findAllById(productCreateRequest.getCategoryIds())
        );
        if (categorySet.size() != productCreateRequest.getCategoryIds().size()) {
            throw new IllegalArgumentException("Invalid categories id");
        }

        Product createdProduct = productMapper.toProduct(productCreateRequest, supplier, categorySet);
        productRepository.save(createdProduct);
    }
}
