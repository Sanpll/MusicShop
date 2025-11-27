package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.request.WarehouseManagerRequest;
import ru.randomplay.musicshop.dto.response.WarehouseManagerResponse;
import ru.randomplay.musicshop.entity.Store;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.entity.WarehouseManager;
import ru.randomplay.musicshop.mapper.WarehouseManagerMapper;
import ru.randomplay.musicshop.repository.StoreRepository;
import ru.randomplay.musicshop.repository.UserRepository;
import ru.randomplay.musicshop.repository.WarehouseManagerRepository;
import ru.randomplay.musicshop.service.WarehouseManagerService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseManagerServiceImpl implements WarehouseManagerService {
    private final WarehouseManagerRepository warehouseManagerRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final WarehouseManagerMapper warehouseManagerMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<WarehouseManagerResponse> getAll() {
        return warehouseManagerMapper.toWarehouseManagerResponseList(warehouseManagerRepository.findAll());
    }

    @Override
    @Transactional
    public void save(WarehouseManagerRequest warehouseManagerRequest) {
        if (userRepository.findByEmail(warehouseManagerRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with this email already exists");
        }

        User createdUser = warehouseManagerMapper.toUser(warehouseManagerRequest);
        createdUser.setPassword(passwordEncoder.encode(warehouseManagerRequest.getPassword()));

        Store store = storeRepository.findById(warehouseManagerRequest
                .getStoreId()).orElseThrow(() -> new IllegalArgumentException("This store doesn't exist"));

        WarehouseManager createdWarehouseManager = warehouseManagerMapper.toWarehouseManager(createdUser, store);
        warehouseManagerRepository.save(createdWarehouseManager);
    }
}
