package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.ProductCreateRequest;

public interface ProductService {
    void save(ProductCreateRequest productCreateRequest);
}
