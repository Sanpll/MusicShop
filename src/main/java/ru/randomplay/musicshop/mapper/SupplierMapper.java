package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.entity.Supplier;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {
    Supplier toSupplier(SupplierCreateRequest supplierCreateRequest);
}
