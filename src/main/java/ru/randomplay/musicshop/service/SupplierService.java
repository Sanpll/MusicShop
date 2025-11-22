package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.entity.Supplier;

import java.util.List;

public interface SupplierService {
    List<Supplier> getAll();

    Supplier save(SupplierCreateRequest supplierCreateRequest);
}
