package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.WarehouseManagerCreateRequest;
import ru.randomplay.musicshop.dto.response.WarehouseManagerResponse;

import java.util.List;

public interface WarehouseManagerService {
    List<WarehouseManagerResponse> getAll();

    void save(WarehouseManagerCreateRequest warehouseManagerCreateRequest);
}
