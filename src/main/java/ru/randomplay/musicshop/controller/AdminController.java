package ru.randomplay.musicshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.randomplay.musicshop.dto.create.*;
import ru.randomplay.musicshop.dto.update.*;
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
    public String dashboard(Model model,
                            @RequestParam(required = false) String table) {
        if (table == null) {
            model.addAttribute("admins", adminService.getAll());
        } else {
            switch (table) {
                case "employees":
                    model.addAttribute("employees", employeeService.getAll());
                    break;
                case "warehouseManagers":
                    model.addAttribute("warehouseManagers", warehouseManagerService.getAll());
                    break;
                case "stores":
                    model.addAttribute("stores", storeService.getAll());
                    break;
                case "categories":
                    model.addAttribute("categories", categoryService.getAll());
                    break;
                default:
                    model.addAttribute("admins", adminService.getAll());
                    break;
            }
        }
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

    @GetMapping("/update/{id}")
    public String updateAdminPage(Model model,
                                  @PathVariable Long id) {
        model.addAttribute("admin", adminService.get(id));
        return "admin/updateAdmin";
    }

    @GetMapping("/update/employee/{id}")
    public String updateEmployeePage(Model model,
                                     @PathVariable Long id) {
        model.addAttribute("employee", employeeService.get(id));
        model.addAttribute("stores", storeService.getAll());
        return "admin/updateEmployee";
    }

    @GetMapping("/update/warehouse-manager/{id}")
    public String updateWarehouseManagerPage(Model model,
                                             @PathVariable Long id) {
        model.addAttribute("warehouseManager", warehouseManagerService.get(id));
        model.addAttribute("stores", storeService.getAll());
        return "admin/updateWarehouseManager";
    }

    @GetMapping("/update/store/{id}")
    public String updateStorePage(Model model,
                                  @PathVariable Long id) {
        model.addAttribute("store", storeService.get(id));
        return "admin/updateStore";
    }

    @GetMapping("/update/category/{id}")
    public String updateCategoryPage(Model model,
                                     @PathVariable Long id) {
        model.addAttribute("category", categoryService.get(id));
        return "admin/updateCategory";
    }

    @PostMapping("/add")
    public String addAdmin(@Valid @ModelAttribute AdminCreateRequest adminCreateRequest) {
        adminService.save(adminCreateRequest);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/add/employee")
    public String addEmployee(@Valid @ModelAttribute EmployeeCreateRequest employeeCreateRequest) {
        employeeService.save(employeeCreateRequest);
        return "redirect:/admin/dashboard?table=employees";
    }

    @PostMapping("/add/warehouse-manager")
    public String addWarehouseManager(@Valid @ModelAttribute WarehouseManagerCreateRequest warehouseManagerCreateRequest) {
        warehouseManagerService.save(warehouseManagerCreateRequest);
        return "redirect:/admin/dashboard?table=warehouseManagers";
    }

    @PostMapping("/add/store")
    public String addStore(@Valid @ModelAttribute StoreCreateRequest storeCreateRequest) {
        storeService.save(storeCreateRequest);
        return "redirect:/admin/dashboard?table=stores";
    }

    @PostMapping("/add/category")
    public String addCategory(@Valid @ModelAttribute CategoryCreateRequest categoryCreateRequest) {
        categoryService.save(categoryCreateRequest);
        return "redirect:/admin/dashboard?table=categories";
    }

    @PostMapping("/update/{id}")
    public String updateAdmin(@PathVariable Long id,
                              @Valid @ModelAttribute AdminUpdateRequest adminUpdateRequest) {
        adminService.update(id, adminUpdateRequest);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/update/employee/{id}")
    public String updateEmployee(@PathVariable Long id,
                                 @Valid @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest) {
        employeeService.update(id, employeeUpdateRequest);
        return "redirect:/admin/dashboard?table=employees";
    }

    @PostMapping("/update/warehouse-manager/{id}")
    public String updateWarehouseManager(@PathVariable Long id,
                                         @Valid @ModelAttribute WarehouseManagerUpdateRequest warehouseManagerUpdateRequest) {
        warehouseManagerService.update(id, warehouseManagerUpdateRequest);
        return "redirect:/admin/dashboard?table=warehouseManagers";
    }

    @PostMapping("/update/store/{id}")
    public String updateStore(@PathVariable Long id,
                              @Valid @ModelAttribute StoreUpdateRequest storeUpdateRequest) {
        storeService.update(id, storeUpdateRequest);
        return "redirect:/admin/dashboard?table=stores";
    }

    @PostMapping("/update/category/{id}")
    public String updateCategory(@PathVariable Long id,
                                 @Valid @ModelAttribute CategoryUpdateRequest categoryUpdateRequest) {
        categoryService.update(id, categoryUpdateRequest);
        return "redirect:/admin/dashboard?table=categories";
    }
}
