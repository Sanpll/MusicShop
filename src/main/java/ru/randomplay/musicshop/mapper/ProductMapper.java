package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.entity.Category;
import ru.randomplay.musicshop.entity.Product;
import ru.randomplay.musicshop.entity.Supplier;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "productCreateRequest.name")
    @Mapping(target = "supplier", source = "supplier")
    @Mapping(target = "categoryLinks", source = "categorySet")
    Product toProduct(ProductCreateRequest productCreateRequest, Supplier supplier, Set<Category> categorySet);
}
