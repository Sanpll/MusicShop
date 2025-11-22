package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.randomplay.musicshop.dto.UserCreateRequest;
import ru.randomplay.musicshop.service.CustomerService;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final CustomerService customerService;

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("error", error);
        return "auth/login";
    }

    @GetMapping("/login-success")
    public String redirectByRole(Authentication authentication) {
        String userRole = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse("ROLE_CUSTOMER");

        return switch (userRole) {
            case "ROLE_EMPLOYEE" -> "redirect:/employee/products";
            case "ROLE_WAREHOUSE_MANAGER" -> "redirect:/warehouse/suppliers";
            case "ROLE_ADMIN" -> "redirect:/admin/...";
            default -> "redirect:/home";
        };
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute UserCreateRequest userCreateRequest) {
        customerService.save(userCreateRequest);
        return "redirect:/home";
    }

}
