package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.request.SupplierRequest;
import ru.randomplay.musicshop.dto.response.SupplierResponse;

import java.util.List;

public interface SupplierService {
    SupplierResponse get(Long id);

    List<SupplierResponse> getAll();

    void save(SupplierRequest supplierRequest);

    void update(Long id, SupplierRequest supplierRequest);
}
