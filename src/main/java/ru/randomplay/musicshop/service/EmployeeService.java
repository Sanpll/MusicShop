package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.EmployeeCreateRequest;
import ru.randomplay.musicshop.dto.response.EmployeeResponse;
import ru.randomplay.musicshop.dto.update.EmployeeUpdateRequest;
import ru.randomplay.musicshop.entity.Employee;

import java.util.List;

public interface EmployeeService {
    Employee getByEmail(String email);

    EmployeeResponse get(Long id);

    List<EmployeeResponse> getAll();

    void save(EmployeeCreateRequest employeeCreateRequest);

    void update(Long id, EmployeeUpdateRequest employeeUpdateRequest);
}
