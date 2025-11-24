package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.create.AdminCreateRequest;
import ru.randomplay.musicshop.dto.response.AdminResponse;
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
    public List<AdminResponse> getAll() {
        return adminMapper.toAdminResponseList(userRepository.findAllByRole(UserRole.ADMIN));
    }

    @Override
    @Transactional
    public void save(AdminCreateRequest adminCreateRequest) {
        if (userRepository.findByEmail(adminCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User createdUser = adminMapper.toUser(adminCreateRequest);
        createdUser.setPassword(passwordEncoder.encode(adminCreateRequest.getPassword()));
        userRepository.save(createdUser);
    }
}
