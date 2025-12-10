package ru.randomplay.musicshop.service;

import org.springframework.web.multipart.MultipartFile;
import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.dto.response.ProductResponse;
import ru.randomplay.musicshop.dto.update.ProductUpdateRequest;
import ru.randomplay.musicshop.model.ProductStatus;

import java.util.List;

public interface ProductService {
    ProductResponse get(Long id);

    List<ProductResponse> getAll();

    List<ProductResponse> getAllByStatus(ProductStatus status);

    void save(ProductCreateRequest productCreateRequest, MultipartFile image);

    void update(Long id, ProductUpdateRequest productUpdateRequest, MultipartFile image);
}
