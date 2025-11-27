package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.request.CustomerRequest;
import ru.randomplay.musicshop.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "CUSTOMER")
    @Mapping(target = "activity", constant = "ACTIVE")
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(CustomerRequest customerRequest);
}
