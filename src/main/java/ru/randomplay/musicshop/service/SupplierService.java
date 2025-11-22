package ru.randomplay.musicshop.service;

import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.entity.Supplier;

public interface SupplierService {
    Supplier save(SupplierCreateRequest supplierCreateRequest);
}
