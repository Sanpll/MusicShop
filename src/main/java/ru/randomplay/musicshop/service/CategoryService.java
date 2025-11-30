package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.CategoryCreateRequest;
import ru.randomplay.musicshop.dto.response.CategoryResponse;
import ru.randomplay.musicshop.dto.update.CategoryUpdateRequest;

import java.util.List;

public interface CategoryService {
    CategoryResponse get(Long id);

    List<CategoryResponse> getAll();

    void save(CategoryCreateRequest categoryCreateRequest);

    void update(Long id, CategoryUpdateRequest categoryUpdateRequest);
}
