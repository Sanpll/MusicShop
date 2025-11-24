package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.create.SupplierCreateRequest;
import ru.randomplay.musicshop.dto.response.SupplierResponse;

import java.util.List;

public interface SupplierService {
    List<SupplierResponse> getAll();

    void save(SupplierCreateRequest supplierCreateRequest);
}
