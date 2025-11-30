package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.randomplay.musicshop.dto.create.WarehouseManagerCreateRequest;
import ru.randomplay.musicshop.dto.response.WarehouseManagerResponse;
import ru.randomplay.musicshop.dto.update.WarehouseManagerUpdateRequest;
import ru.randomplay.musicshop.entity.Store;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.entity.WarehouseManager;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface WarehouseManagerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "WAREHOUSE_MANAGER")
    @Mapping(target = "activity", constant = "ACTIVE")
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(WarehouseManagerCreateRequest warehouseManagerCreateRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "supplies", ignore = true)
    WarehouseManager toWarehouseManager(User user, Store store);

    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "activity", source = "user.activity")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "registeredAt", source = "user.registeredAt")
    @Mapping(target = "storeLocation", source = "store.location")
    WarehouseManagerResponse toWarehouseManagerResponse(WarehouseManager warehouseManager);

    List<WarehouseManagerResponse> toWarehouseManagerResponseList(List<WarehouseManager> warehouseManagers);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "supplies", ignore = true)
    @Mapping(target = "user.firstName", source = "warehouseManagerUpdateRequest.firstName")
    @Mapping(target = "user.lastName", source = "warehouseManagerUpdateRequest.lastName")
    @Mapping(target = "user.phone", source = "warehouseManagerUpdateRequest.phone")
    @Mapping(target = "user.activity", source = "warehouseManagerUpdateRequest.activity")
    @Mapping(target = "status", source = "warehouseManagerUpdateRequest.status")
    void updateWarehouseManager(@MappingTarget WarehouseManager warehouseManager, WarehouseManagerUpdateRequest warehouseManagerUpdateRequest, Store store);
}
