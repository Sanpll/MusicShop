package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.create.EmployeeCreateRequest;
import ru.randomplay.musicshop.dto.response.EmployeeResponse;
import ru.randomplay.musicshop.dto.update.EmployeeUpdateRequest;
import ru.randomplay.musicshop.entity.Employee;
import ru.randomplay.musicshop.entity.Store;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.mapper.EmployeeMapper;
import ru.randomplay.musicshop.repository.EmployeeRepository;
import ru.randomplay.musicshop.repository.StoreRepository;
import ru.randomplay.musicshop.repository.UserRepository;
import ru.randomplay.musicshop.service.EmployeeService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final UserRepository userRepository;
    private final EmployeeRepository employeeRepository;
    private final StoreRepository storeRepository;
    private final EmployeeMapper employeeMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public EmployeeResponse get(Long id) {
        return employeeMapper.toEmployeeResponse(employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee with ID " + id + " doesn't exist")));
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return employeeMapper.toEmployeeResponseList(employeeRepository.findAllWithUserAndStore());
    }

    @Override
    @Transactional
    public void save(EmployeeCreateRequest employeeCreateRequest) {
        if (userRepository.findByEmail(employeeCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + employeeCreateRequest.getEmail() + " already exists");
        }

        User createdUser = employeeMapper.toUser(employeeCreateRequest);
        createdUser.setPassword(passwordEncoder.encode(employeeCreateRequest.getPassword()));

        Store store = storeRepository.findById(employeeCreateRequest
                .getStoreId()).orElseThrow(() -> new IllegalArgumentException("Store with ID " + employeeCreateRequest.getStoreId() + " doesn't exist"));

        Employee createdEmployee = employeeMapper.toEmployee(createdUser, store);
        employeeRepository.save(createdEmployee);
    }

    @Transactional
    @Override
    public void update(Long id, EmployeeUpdateRequest employeeUpdateRequest) {
        Employee updatedEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee with ID " + id + " doesn't exist"));
        Store employeeStore = storeRepository.findById(employeeUpdateRequest.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store with ID " + employeeUpdateRequest.getStoreId() + " doesn't exist"));

        employeeMapper.updateEmployee(updatedEmployee, employeeUpdateRequest, employeeStore);
        employeeRepository.save(updatedEmployee);
    }
}
