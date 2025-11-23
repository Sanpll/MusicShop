package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.dto.SupplierResponse;
import ru.randomplay.musicshop.entity.Supplier;
import ru.randomplay.musicshop.mapper.SupplierMapper;
import ru.randomplay.musicshop.repository.SupplierRepository;
import ru.randomplay.musicshop.service.SupplierService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl  implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public List<SupplierResponse> getAll() {
        return supplierMapper.toSupplierRespondeList(supplierRepository.findAll());
    }

    @Override
    public void save(SupplierCreateRequest supplierCreateRequest) {
        if (supplierRepository.findByName(supplierCreateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Supplier with this name already exists");
        }

        Supplier createdSupplier = supplierMapper.toSupplier(supplierCreateRequest);
        supplierRepository.save(createdSupplier);
    }
}
