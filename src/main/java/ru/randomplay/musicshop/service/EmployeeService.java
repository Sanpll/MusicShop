package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.request.EmployeeRequest;
import ru.randomplay.musicshop.dto.response.EmployeeResponse;

import java.util.List;

public interface EmployeeService {
    List<EmployeeResponse> getAll();

    void save(EmployeeRequest employeeRequest);
}
