package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.dto.SupplierResponse;
import ru.randomplay.musicshop.service.SupplierService;

import java.util.List;

@Controller
@RequestMapping("/warehouse")
@PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
@RequiredArgsConstructor
public class WarehouseController {
    private final SupplierService supplierService;

    @GetMapping("/suppliers")
    public String suppliersPage(Model model) {
        List<SupplierResponse> suppliers = supplierService.getAll();
        model.addAttribute("suppliers", suppliers);
        return "warehouse/suppliers";
    }

    @GetMapping("/add/supplier")
    public String newSupplierPage() {
        return "warehouse/newSupplier";
    }

    @PostMapping("/add/supplier")
    public String newSupplier(@ModelAttribute SupplierCreateRequest supplierCreateRequest) {
        supplierService.save(supplierCreateRequest);
        return "redirect:/warehouse/suppliers";
    }
}
