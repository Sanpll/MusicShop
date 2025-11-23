package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.dto.SupplierResponse;

import java.util.List;

public interface SupplierService {
    List<SupplierResponse> getAll();

    void save(SupplierCreateRequest supplierCreateRequest);
}
