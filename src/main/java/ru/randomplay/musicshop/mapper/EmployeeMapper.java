package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public class EmployeeMapper {
    // примерно должно быть так
//    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "role", constant = "EMPLOYEE")
//    @Mapping(target = "activity", constant = "ACTIVE")
//    @Mapping(target = "registeredAt", expression = "java(java.time.LocalDateTime.now())")
//    User toUser(EmployeeCreateRequest request);
//
//    @Mapping(target = "store", source = "storeId")
//    Employee toEmployee(User user, Long storeId);
}
