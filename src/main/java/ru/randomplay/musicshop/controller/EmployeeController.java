package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.randomplay.musicshop.service.ProductService;

@Controller
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
@RequiredArgsConstructor
public class EmployeeController {
    private final ProductService productService;

    @GetMapping("/dashboard")
    public String productsPage(Model model) {
        model.addAttribute("products", productService.getAll());
        return "employee/dashboard";
    }
}
