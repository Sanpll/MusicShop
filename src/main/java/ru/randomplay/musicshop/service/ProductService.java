package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.request.ProductRequest;
import ru.randomplay.musicshop.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> getAll();

    void save(ProductRequest productRequest);
}
