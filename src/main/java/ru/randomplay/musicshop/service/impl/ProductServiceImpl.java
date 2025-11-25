package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.service.ProductService;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {


    @Override
    public void save(ProductCreateRequest productCreateRequest) {

    }
}
