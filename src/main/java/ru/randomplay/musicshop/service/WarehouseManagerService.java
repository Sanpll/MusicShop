package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.WarehouseManagerCreateRequest;
import ru.randomplay.musicshop.dto.response.WarehouseManagerResponse;
import ru.randomplay.musicshop.dto.update.WarehouseManagerUpdateRequest;

import java.util.List;

public interface WarehouseManagerService {
    WarehouseManagerResponse get(Long id);

    List<WarehouseManagerResponse> getAll();

    void save(WarehouseManagerCreateRequest warehouseManagerCreateRequest);

    void update(Long id, WarehouseManagerUpdateRequest warehouseManagerUpdateRequest);
}
