package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.CategoryCreateRequest;
import ru.randomplay.musicshop.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();

    void save(CategoryCreateRequest categoryCreateRequest);
}
