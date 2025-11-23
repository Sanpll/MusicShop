package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.randomplay.musicshop.dto.AdminCreateRequest;
import ru.randomplay.musicshop.service.AdminService;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/dashboard")
    public String dashboard() {
        return "admin/dashboard";
    }

    @GetMapping("/add/store")
    public String addStorePage() {
        return "admin/newEmployee";
    }

    @GetMapping("/add/employee")
    public String addEmployeePage() {
        return "admin/newEmployee";
    }

    @GetMapping("/add")
    public String addAdminPage() {
        return "admin/newAdmin";
    }

    @PostMapping("/add")
    public String registration(@ModelAttribute AdminCreateRequest adminCreateRequest) {
        adminService.save(adminCreateRequest);
        return "redirect:/admin/dashboard";
    }

}
