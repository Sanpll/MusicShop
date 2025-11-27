package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.request.ProductRequest;
import ru.randomplay.musicshop.dto.response.ProductResponse;
import ru.randomplay.musicshop.entity.Product;
import ru.randomplay.musicshop.entity.Supplier;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, imports = {Collectors.class})
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "name", source = "productRequest.name")
    @Mapping(target = "supplier", source = "supplier")
    @Mapping(target = "categoryLinks", ignore = true)
    Product toProduct(ProductRequest productRequest, Supplier supplier);

    @Mapping(target = "supplierName", source = "product.supplier.name")
    @Mapping(target = "categoryNames", expression = "java(product.getCategoryLinks().stream()" +
            ".map(link -> link.getCategory().getName())" +
            ".collect(Collectors.toList()))")
    ProductResponse toProductResponse(Product product);

    List<ProductResponse> toProductResponseList(List<Product> products);
}
