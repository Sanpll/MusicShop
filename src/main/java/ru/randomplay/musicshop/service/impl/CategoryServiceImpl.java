package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.create.CategoryCreateRequest;
import ru.randomplay.musicshop.dto.response.CategoryResponse;
import ru.randomplay.musicshop.dto.update.CategoryUpdateRequest;
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
    public CategoryResponse get(Long id) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with this ID doesn't exist")));
    }

    @Override
    public List<CategoryResponse> getAll() {
        return categoryMapper.toCategoryResponseList(categoryRepository.findAll());
    }

    @Override
    public void save(CategoryCreateRequest categoryCreateRequest) {
        if (categoryRepository.findByName(categoryCreateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Category with this name already exists");
        }

        Category createdCategory = categoryMapper.toCategory(categoryCreateRequest);
        categoryRepository.save(createdCategory);
    }

    @Override
    public void update(Long id, CategoryUpdateRequest categoryUpdateRequest) {
        Category updatedCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with this ID doesn't exist"));

        // проверка на то, что новое имя не будет совпадать с уже существующими
        if (!updatedCategory.getName().equals(categoryUpdateRequest.getName()) &&
                categoryRepository.findByName(categoryUpdateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Category with this name already exists");
        }

        categoryMapper.updateCategory(updatedCategory, categoryUpdateRequest);
        categoryRepository.save(updatedCategory);
    }
}
