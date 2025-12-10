package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.create.WarehouseManagerCreateRequest;
import ru.randomplay.musicshop.dto.response.WarehouseManagerResponse;
import ru.randomplay.musicshop.dto.update.WarehouseManagerUpdateRequest;
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
    public WarehouseManagerResponse get(Long id) {
        return warehouseManagerMapper.toWarehouseManagerResponse(warehouseManagerRepository.findByIdWithUserAndStore(id)
                .orElseThrow(() -> new IllegalArgumentException("Warehouse manager with ID " + id + " doesn't exist")));
    }

    @Override
    public List<WarehouseManagerResponse> getAll() {
        return warehouseManagerMapper.toWarehouseManagerResponseList(warehouseManagerRepository.findAllWithUserAndStore());
    }

    @Override
    @Transactional
    public void save(WarehouseManagerCreateRequest warehouseManagerCreateRequest) {
        if (userRepository.findByEmail(warehouseManagerCreateRequest.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User with email " + warehouseManagerCreateRequest.getEmail() + " already exists");
        }

        User createdUser = warehouseManagerMapper.toUser(warehouseManagerCreateRequest);
        createdUser.setPassword(passwordEncoder.encode(warehouseManagerCreateRequest.getPassword()));

        Store store = storeRepository.findById(warehouseManagerCreateRequest.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store with ID " + warehouseManagerCreateRequest.getStoreId() + " doesn't exist"));

        WarehouseManager createdWarehouseManager = warehouseManagerMapper.toWarehouseManager(createdUser, store);
        warehouseManagerRepository.save(createdWarehouseManager);
    }

    @Transactional
    @Override
    public void update(Long id, WarehouseManagerUpdateRequest warehouseManagerUpdateRequest) {
        WarehouseManager updatedWarehouseManager = warehouseManagerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee with ID " + id + " doesn't exist"));
        Store warehouseManagerStore = storeRepository.findById(warehouseManagerUpdateRequest.getStoreId())
                .orElseThrow(() -> new IllegalArgumentException("Store with ID " + warehouseManagerUpdateRequest.getStoreId() + " doesn't exist"));

        warehouseManagerMapper.updateWarehouseManager(updatedWarehouseManager, warehouseManagerUpdateRequest, warehouseManagerStore);
        warehouseManagerRepository.save(updatedWarehouseManager);
    }
}
