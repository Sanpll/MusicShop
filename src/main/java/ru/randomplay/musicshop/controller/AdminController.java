package ru.randomplay.musicshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.randomplay.musicshop.dto.create.*;
import ru.randomplay.musicshop.service.*;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final WarehouseManagerService warehouseManagerService;
    private final StoreService storeService;
    private final CategoryService categoryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("admins", adminService.getAll());
        model.addAttribute("employees", employeeService.getAll());
        model.addAttribute("warehouseManagers", warehouseManagerService.getAll());
        model.addAttribute("stores", storeService.getAll());
        return "admin/dashboard";
    }

    @GetMapping("/add")
    public String addAdminPage() {
        return "admin/newAdmin";
    }

    @GetMapping("/add/employee")
    public String addEmployeePage(Model model) {
        model.addAttribute("stores", storeService.getAll());
        return "admin/newEmployee";
    }

    @GetMapping("/add/warehouse-manager")
    public String addWarehouseManagerPage(Model model) {
        model.addAttribute("stores", storeService.getAll());
        return "admin/newWarehouseManager";
    }

    @GetMapping("/add/store")
    public String addStorePage() {
        return "admin/newStore";
    }

    @GetMapping("/add/category")
    public String addCategoryPage() {
        return "admin/newCategory";
    }

    @PostMapping("/add")
    public String addAdmin(@Valid @ModelAttribute AdminCreateRequest adminCreateRequest) {
        adminService.save(adminCreateRequest);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/add/employee")
    public String addEmployee(@Valid @ModelAttribute EmployeeCreateRequest employeeCreateRequest) {
        employeeService.save(employeeCreateRequest);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/add/warehouse-manager")
    public String addWarehouseManager(@Valid @ModelAttribute WarehouseManagerCreateRequest warehouseManagerCreateRequest) {
        warehouseManagerService.save(warehouseManagerCreateRequest);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/add/store")
    public String addStore(@Valid @ModelAttribute StoreCreateRequest storeCreateRequest) {
        storeService.save(storeCreateRequest);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/add/category")
    public String addCategory(@Valid @ModelAttribute CategoryCreateRequest categoryCreateRequest) {
        categoryService.save(categoryCreateRequest);
        return "redirect:/admin/dashboard";
    }

}
