package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.randomplay.musicshop.dto.request.SupplierRequest;
import ru.randomplay.musicshop.dto.response.SupplierResponse;
import ru.randomplay.musicshop.entity.Supplier;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Supplier toSupplier(SupplierRequest supplierRequest);

    SupplierResponse toSupplierResponse(Supplier supplier);

    List<SupplierResponse> toSupplierResponseList(List<Supplier> suppliers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateSupplier(@MappingTarget Supplier supplier, SupplierRequest supplierRequest);

}
