package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    ProductResponse get(Long id);

    List<ProductResponse> getAll();

    void save(ProductCreateRequest productCreateRequest);
}
