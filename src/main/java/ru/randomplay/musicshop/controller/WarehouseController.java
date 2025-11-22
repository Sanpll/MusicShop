package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.randomplay.musicshop.dto.SupplierCreateRequest;
import ru.randomplay.musicshop.entity.Supplier;
import ru.randomplay.musicshop.service.SupplierService;

import java.util.List;

@Controller
@RequestMapping("/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final SupplierService supplierService;

    @GetMapping("/suppliers")
    public String suppliersPage(Model model) {
        List<Supplier> suppliers = supplierService.getAll();
        model.addAttribute("suppliers", suppliers);
        return "warehouse/suppliers";
    }

    @GetMapping("/suppliers/add")
    public String newSupplierPage() {
        return "warehouse/newSupplier";
    }

    @PostMapping("/suppliers/add")
    public String newSupplier(@ModelAttribute SupplierCreateRequest supplierCreateRequest) {
        supplierService.save(supplierCreateRequest);
        return "redirect:/warehouse/suppliers";
    }
}
