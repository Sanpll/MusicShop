package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.request.StoreRequest;
import ru.randomplay.musicshop.dto.response.StoreResponse;
import ru.randomplay.musicshop.entity.Store;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface StoreMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "warehouseManagers", ignore = true)
    @Mapping(target = "employees", ignore = true)
    Store toStore(StoreRequest storeRequest);

    List<StoreResponse> toStoreResponseList(List<Store> stores);
}
