package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.request.CategoryRequest;
import ru.randomplay.musicshop.dto.response.CategoryResponse;
import ru.randomplay.musicshop.entity.Category;
import ru.randomplay.musicshop.mapper.CategoryMapper;
import ru.randomplay.musicshop.repository.CategoryRepository;
import ru.randomplay.musicshop.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAll() {
        return categoryMapper.toCategoryResponseList(categoryRepository.findAll());
    }

    @Override
    public void save(CategoryRequest categoryRequest) {
        if (categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Category with this name already exists");
        }

        Category createdCategory = categoryMapper.toCategory(categoryRequest);
        categoryRepository.save(createdCategory);
    }
}
