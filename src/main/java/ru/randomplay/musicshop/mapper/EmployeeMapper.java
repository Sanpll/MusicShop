package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import ru.randomplay.musicshop.dto.create.EmployeeCreateRequest;
import ru.randomplay.musicshop.dto.response.EmployeeResponse;
import ru.randomplay.musicshop.entity.Employee;
import ru.randomplay.musicshop.entity.Store;
import ru.randomplay.musicshop.entity.User;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "EMPLOYEE")
    @Mapping(target = "activity", constant = "ACTIVE")
    @Mapping(target = "registeredAt", ignore = true)
    @Mapping(target = "authorities", ignore = true)
    User toUser(EmployeeCreateRequest employeeCreateRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "store", source = "store")
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "orders", ignore = true)
    Employee toEmployee(User user, Store store);

    @Mapping(target = "email", source = "employee.user.email")
    @Mapping(target = "activity", source = "employee.user.activity")
    @Mapping(target = "firstName", source = "employee.user.firstName")
    @Mapping(target = "lastName", source = "employee.user.lastName")
    @Mapping(target = "phone", source = "employee.user.phone")
    @Mapping(target = "registeredAt", source = "employee.user.registeredAt")
    @Mapping(target = "storeLocation", source = "employee.store.location")
    EmployeeResponse toEmployeeResponse(Employee employee);

    List<EmployeeResponse> toEmployeeResponseList(List<Employee> employees);
}
