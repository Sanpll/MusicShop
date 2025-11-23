package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.AdminCreateRequest;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.mapper.AdminMapper;
import ru.randomplay.musicshop.repository.UserRepository;
import ru.randomplay.musicshop.service.AdminService;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminMapper adminMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void save(AdminCreateRequest adminCreateRequest) {
        if (userRepository.findByEmail(adminCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User createdUser = adminMapper.toUser(adminCreateRequest);
        createdUser.setPassword(passwordEncoder.encode(adminCreateRequest.getPassword()));
        userRepository.save(createdUser);
    }
}
