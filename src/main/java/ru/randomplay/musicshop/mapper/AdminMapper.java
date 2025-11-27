package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.request.AdminRequest;
import ru.randomplay.musicshop.dto.response.AdminResponse;
import ru.randomplay.musicshop.entity.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AdminMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "ADMIN")
    @Mapping(target = "activity", constant = "ACTIVE")
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(AdminRequest adminRequest);

    AdminResponse toAdminResponse(User user);

    List<AdminResponse> toAdminResponseList(List<User> users);
}
