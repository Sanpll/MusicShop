package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.service.SupplierService;

@Controller("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    SupplierService supplierService;

    @GetMapping("/suppliers")
    public String suppliersPage() {
        return "suppliers";
    }

    @GetMapping("/suppliers/add")
    public String newSupplierPage() {
        return "newSupplier";
    }

    @PostMapping("/suppliers/add")
    public String newSupplier(@ModelAttribute SupplierCreateRequest supplierCreateRequest) {
        supplierService.save(supplierCreateRequest);
        return "redirect:/suppliers";
    }
}
