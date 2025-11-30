package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.randomplay.musicshop.dto.create.CategoryCreateRequest;
import ru.randomplay.musicshop.dto.response.CategoryResponse;
import ru.randomplay.musicshop.dto.update.CategoryUpdateRequest;
import ru.randomplay.musicshop.entity.Category;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productLinks", ignore = true)
    Category toCategory(CategoryCreateRequest categoryCreateRequest);

    CategoryResponse toCategoryResponse(Category category);

    List<CategoryResponse> toCategoryResponseList(List<Category> category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "productLinks", ignore = true)
    void updateCategory(@MappingTarget Category category, CategoryUpdateRequest categoryUpdateRequest);
}
