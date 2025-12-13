package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.create.CustomerCreateRequest;
import ru.randomplay.musicshop.dto.response.CustomerResponse;
import ru.randomplay.musicshop.entity.Customer;
import ru.randomplay.musicshop.entity.User;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "CUSTOMER")
    @Mapping(target = "activity", constant = "ACTIVE")
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(CustomerCreateRequest customerCreateRequest);

    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "registeredAt", source = "user.registeredAt")
    CustomerResponse toCustomerResponse(Customer customer);
}
