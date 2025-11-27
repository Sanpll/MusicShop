package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.request.WarehouseManagerRequest;
import ru.randomplay.musicshop.dto.response.WarehouseManagerResponse;
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
    User toUser(WarehouseManagerRequest warehouseManagerRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "store", source = "store")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "supplies", ignore = true)
    WarehouseManager toWarehouseManager(User user, Store store);

    @Mapping(target = "email", source = "warehouseManager.user.email")
    @Mapping(target = "activity", source = "warehouseManager.user.activity")
    @Mapping(target = "firstName", source = "warehouseManager.user.firstName")
    @Mapping(target = "lastName", source = "warehouseManager.user.lastName")
    @Mapping(target = "phone", source = "warehouseManager.user.phone")
    @Mapping(target = "registeredAt", source = "warehouseManager.user.registeredAt")
    @Mapping(target = "storeLocation", source = "warehouseManager.store.location")
    WarehouseManagerResponse toWarehouseManagerResponse(WarehouseManager warehouseManager);

    List<WarehouseManagerResponse> toWarehouseManagerResponseList(List<WarehouseManager> warehouseManagers);
}
