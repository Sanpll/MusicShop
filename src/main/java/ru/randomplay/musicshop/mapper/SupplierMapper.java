package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.randomplay.musicshop.dto.create.SupplierCreateRequest;
import ru.randomplay.musicshop.dto.response.SupplierResponse;
import ru.randomplay.musicshop.dto.update.SupplierUpdateRequest;
import ru.randomplay.musicshop.entity.Supplier;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    Supplier toSupplier(SupplierCreateRequest supplierCreateRequest);

    SupplierResponse toSupplierResponse(Supplier supplier);

    List<SupplierResponse> toSupplierResponseList(List<Supplier> suppliers);

    // нужно именно обновлять entity внутри метода, чтобы не создавать новую сущность, т.к. у неё будет другой id
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "products", ignore = true)
    void updateSupplier(@MappingTarget Supplier supplier, SupplierUpdateRequest supplierUpdateRequest);

}
