package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.randomplay.musicshop.dto.update.CustomerUpdateRequest;
import ru.randomplay.musicshop.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "activity", ignore = true)
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    void updateUser(@MappingTarget User user, CustomerUpdateRequest customerUpdateRequest);
}
