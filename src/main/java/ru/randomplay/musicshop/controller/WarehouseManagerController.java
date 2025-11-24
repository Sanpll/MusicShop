package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.randomplay.musicshop.dto.create.SupplierCreateRequest;
import ru.randomplay.musicshop.service.SupplierService;

@Controller
@RequestMapping("/warehouse-manager")
@PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
@RequiredArgsConstructor
public class WarehouseManagerController {
    private final SupplierService supplierService;

    @GetMapping("/suppliers")
    public String suppliersPage(Model model) {
        model.addAttribute("suppliers", supplierService.getAll());
        return "warehouse/suppliers";
    }

    @GetMapping("/add/supplier")
    public String newSupplierPage() {
        return "warehouse/newSupplier";
    }

    @GetMapping("/add/product")
    public String newProductPage() {
        return "warehouse/newProduct";
    }

    @PostMapping("/add/supplier")
    public String newSupplier(@ModelAttribute SupplierCreateRequest supplierCreateRequest) {
        supplierService.save(supplierCreateRequest);
        return "redirect:/warehouse/suppliers";
    }
}
