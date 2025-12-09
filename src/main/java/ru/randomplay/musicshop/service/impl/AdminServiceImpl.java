package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.create.AdminCreateRequest;
import ru.randomplay.musicshop.dto.response.AdminResponse;
import ru.randomplay.musicshop.dto.update.AdminUpdateRequest;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.mapper.AdminMapper;
import ru.randomplay.musicshop.model.UserRole;
import ru.randomplay.musicshop.repository.UserRepository;
import ru.randomplay.musicshop.service.AdminService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminResponse get(Long id) {
        return adminMapper.toAdminResponse(userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin with ID " + id + " doesn't exist")));
    }

    @Override
    public List<AdminResponse> getAll() {
        return adminMapper.toAdminResponseList(userRepository.findAllByRole(UserRole.ADMIN));
    }

    @Override
    public void save(AdminCreateRequest adminCreateRequest) {
        if (userRepository.findByEmail(adminCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + adminCreateRequest.getEmail() + " already exists");
        }

        User createdUser = adminMapper.toUser(adminCreateRequest);
        createdUser.setPassword(passwordEncoder.encode(adminCreateRequest.getPassword()));
        userRepository.save(createdUser);
    }

    @Override
    public void update(Long id, AdminUpdateRequest adminUpdateRequest) {
        User updatedUser = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin with ID " + id + " doesn't exist"));

        adminMapper.updateUser(updatedUser, adminUpdateRequest);
        userRepository.save(updatedUser);
    }
}
