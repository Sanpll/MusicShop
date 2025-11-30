package ru.randomplay.musicshop.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.randomplay.musicshop.dto.create.EmployeeCreateRequest;
import ru.randomplay.musicshop.dto.response.EmployeeResponse;
import ru.randomplay.musicshop.dto.update.EmployeeUpdateRequest;
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
    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "orders", ignore = true)
    Employee toEmployee(User user, Store store);

    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "activity", source = "user.activity")
    @Mapping(target = "firstName", source = "user.firstName")
    @Mapping(target = "lastName", source = "user.lastName")
    @Mapping(target = "phone", source = "user.phone")
    @Mapping(target = "registeredAt", source = "user.registeredAt")
    @Mapping(target = "storeLocation", source = "store.location")
    EmployeeResponse toEmployeeResponse(Employee employee);

    List<EmployeeResponse> toEmployeeResponseList(List<Employee> employees);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "user.firstName", source = "employeeUpdateRequest.firstName")
    @Mapping(target = "user.lastName", source = "employeeUpdateRequest.lastName")
    @Mapping(target = "user.phone", source = "employeeUpdateRequest.phone")
    @Mapping(target = "user.activity", source = "employeeUpdateRequest.activity")
    @Mapping(target = "status", source = "employeeUpdateRequest.status")
    void updateEmployee(@MappingTarget Employee employee, EmployeeUpdateRequest employeeUpdateRequest, Store store);
}
