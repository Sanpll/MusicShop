package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.request.CategoryRequest;
import ru.randomplay.musicshop.dto.response.CategoryResponse;

import java.util.List;

public interface CategoryService {
    List<CategoryResponse> getAll();

    void save(CategoryRequest categoryRequest);
}
