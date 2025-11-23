package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.CustomerCreateRequest;
import ru.randomplay.musicshop.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "CUSTOMER")
    @Mapping(target = "activity", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    User toUser(CustomerCreateRequest customerCreateRequest);
}
