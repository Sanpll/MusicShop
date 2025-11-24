package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.create.StoreCreateRequest;
import ru.randomplay.musicshop.dto.response.StoreResponse;
import ru.randomplay.musicshop.entity.Store;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StoreMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "warehouseManagers", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Store toStore(StoreCreateRequest storeCreateRequest);

    List<StoreResponse> toStoreResponseList(List<Store> stores);
}
