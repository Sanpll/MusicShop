package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.dto.SupplierResponse;
import ru.randomplay.musicshop.entity.Supplier;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Supplier toSupplier(SupplierCreateRequest supplierCreateRequest);

    List<SupplierResponse> toSupplierRespondeList(List<Supplier> suppliers);
}
