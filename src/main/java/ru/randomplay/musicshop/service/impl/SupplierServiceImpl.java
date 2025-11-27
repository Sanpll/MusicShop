package ru.randomplay.musicshop.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.randomplay.musicshop.dto.create.SupplierCreateRequest;
import ru.randomplay.musicshop.dto.response.SupplierResponse;
import ru.randomplay.musicshop.entity.Supplier;
import ru.randomplay.musicshop.mapper.SupplierMapper;
import ru.randomplay.musicshop.repository.SupplierRepository;
import ru.randomplay.musicshop.service.SupplierService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    @Override
    public SupplierResponse get(Long id) {
        return supplierMapper.toSupplierResponse(supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with this ID doesn't exist")));
    }

    @Override
    public List<SupplierResponse> getAll() {
        return supplierMapper.toSupplierResponseList(supplierRepository.findAll());
    }

    @Override
    @Transactional
    public void save(SupplierCreateRequest supplierCreateRequest) {
        if (supplierRepository.findByName(supplierCreateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Supplier with this name already exists");
        }

        Supplier createdSupplier = supplierMapper.toSupplier(supplierCreateRequest);
        supplierRepository.save(createdSupplier);
    }

    @Override
    @Transactional
    public void update(Long id, SupplierCreateRequest supplierCreateRequest) {
        Supplier updatedSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Supplier with this ID doesn't exist"));

        // проверка на то, что новое имя не будет совпадать с уже существующими
        if (!updatedSupplier.getName().equals(supplierCreateRequest.getName()) &&
                supplierRepository.findByName(supplierCreateRequest.getName()).isPresent()) {
            throw new IllegalArgumentException("Supplier with this name already exists");
        }

        supplierMapper.updateSupplier(updatedSupplier, supplierCreateRequest);
        supplierRepository.save(updatedSupplier);
    }
}
