package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.AdminCreateRequest;
import ru.randomplay.musicshop.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "ADMIN")
    @Mapping(target = "activity", constant = "ACTIVE")
    @Mapping(target = "registeredAt", ignore = true)
    User toUser(AdminCreateRequest adminCreateRequest);
}
