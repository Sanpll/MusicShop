package ru.randomplay.musicshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.randomplay.musicshop.dto.create.AdminCreateRequest;
import ru.randomplay.musicshop.dto.create.EmployeeCreateRequest;
import ru.randomplay.musicshop.dto.create.StoreCreateRequest;
import ru.randomplay.musicshop.dto.create.WarehouseManagerCreateRequest;
import ru.randomplay.musicshop.dto.response.*;
import ru.randomplay.musicshop.dto.update.AdminUpdateRequest;
import ru.randomplay.musicshop.dto.update.EmployeeUpdateRequest;
import ru.randomplay.musicshop.dto.update.StoreUpdateRequest;
import ru.randomplay.musicshop.dto.update.WarehouseManagerUpdateRequest;
import ru.randomplay.musicshop.model.PaymentStatus;
import ru.randomplay.musicshop.model.StoreStatus;
import ru.randomplay.musicshop.model.WorkerStatus;
import ru.randomplay.musicshop.service.*;

import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    private final EmployeeService employeeService;
    private final WarehouseManagerService warehouseManagerService;
    private final StoreService storeService;
    private final OrderService orderService;

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @RequestParam(required = false) String table) {
        if (table == null) {
            List<AdminResponse> admins = adminService.getAll();
            model.addAttribute("admins", admins);
            model.addAttribute("adminsActive", admins.stream().filter(admin -> Objects.equals(admin.getActivity(), "ACTIVE")).count());
            model.addAttribute("adminsDeleted", admins.stream().filter(admin -> Objects.equals(admin.getActivity(), "DELETED")).count());
            return "admin/admins";
        }
        switch (table) {
            case "employees":
                List<EmployeeResponse> employees = employeeService.getAll();
                model.addAttribute("employees", employees);
                model.addAttribute("employeesActive", employees.stream().filter(employee -> Objects.equals(employee.getStatus(), WorkerStatus.ACTIVE)).count());
                model.addAttribute("employeesOnLeave", employees.stream().filter(employee -> Objects.equals(employee.getStatus(), WorkerStatus.ON_LEAVE)).count());
                model.addAttribute("employeesFired", employees.stream().filter(employee -> Objects.equals(employee.getStatus(), WorkerStatus.FIRED)).count());
                return "admin/employees";
            case "warehouseManagers":
                List<WarehouseManagerResponse> warehouseManagers = warehouseManagerService.getAll();
                model.addAttribute("warehouseManagers", warehouseManagers);
                model.addAttribute("warehouseManagersActive", warehouseManagers.stream().filter(warehouseManager -> Objects.equals(warehouseManager.getStatus(), WorkerStatus.ACTIVE)).count());
                model.addAttribute("warehouseManagersOnLeave", warehouseManagers.stream().filter(warehouseManager -> Objects.equals(warehouseManager.getStatus(), WorkerStatus.ON_LEAVE)).count());
                model.addAttribute("warehouseManagersFired", warehouseManagers.stream().filter(warehouseManager -> Objects.equals(warehouseManager.getStatus(), WorkerStatus.FIRED)).count());
                return "admin/warehouseManagers";
            case "stores":
                List<StoreResponse> stores = storeService.getAll();
                model.addAttribute("stores", stores);
                model.addAttribute("storesActive", stores.stream().filter(store -> Objects.equals(store.getStatus(), StoreStatus.ACTIVE)).count());
                model.addAttribute("storesClosed", stores.stream().filter(store -> Objects.equals(store.getStatus(), StoreStatus.CLOSED)).count());
                model.addAttribute("storesInactive", stores.stream().filter(store -> Objects.equals(store.getStatus(), StoreStatus.INACTIVE)).count());
                return "admin/stores";
            case "orders":
                List<OrderResponse> orders = orderService.getAll();
                model.addAttribute("orders", orders);
                model.addAttribute("ordersPaid", orders.stream().filter(order -> Objects.equals(order.getStatus(), PaymentStatus.PAID)).count());
                model.addAttribute("ordersFailed", orders.stream().filter(order -> Objects.equals(order.getStatus(), PaymentStatus.FAILED)).count());
                model.addAttribute("ordersWaiting", orders.stream().filter(order -> Objects.equals(order.getEmployeeLastName(), null)).count());
                return "admin/orders";
            default:
                List<AdminResponse> admins = adminService.getAll();
                model.addAttribute("admins", admins);
                model.addAttribute("adminsActive", admins.stream().filter(admin -> Objects.equals(admin.getActivity(), "ACTIVE")).count());
                model.addAttribute("adminsDeleted", admins.stream().filter(admin -> Objects.equals(admin.getActivity(), "DELETED")).count());
                return "admin/admins";
        }
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
}
