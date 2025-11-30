package ru.randomplay.musicshop.service;

import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.create.EmployeeCreateRequest;
import ru.randomplay.musicshop.dto.response.EmployeeResponse;
import ru.randomplay.musicshop.dto.update.EmployeeUpdateRequest;

import java.util.List;

public interface EmployeeService {
    EmployeeResponse get(Long id);

    List<EmployeeResponse> getAll();

    void save(EmployeeCreateRequest employeeCreateRequest);

    @Transactional
    void update(Long id, EmployeeUpdateRequest employeeUpdateRequest);
}
