package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.EmployeeCreateRequest;
import ru.randomplay.musicshop.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl {
    private final EmployeeRepository employeeRepository;

    public void save(EmployeeCreateRequest employeeCreateRequest) {

    }
}
