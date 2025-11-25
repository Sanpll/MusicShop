package ru.randomplay.musicshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.randomplay.musicshop.dto.create.CustomerCreateRequest;
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
                .findFirst().orElseThrow(() -> new AccessDeniedException("Unable to determine user role"));

        return switch (userRole) {
            //noinspection SpringMVCViewInspection
            case "ROLE_EMPLOYEE" -> "redirect:/employee/products";
            //noinspection SpringMVCViewInspection
            case "ROLE_WAREHOUSE_MANAGER" -> "redirect:/warehouse-manager/suppliers";
            //noinspection SpringMVCViewInspection
            case "ROLE_ADMIN" -> "redirect:/admin/dashboard";
            default -> "redirect:/home";
        };
    }

    @GetMapping("/registration")
    public String registrationPage() {
        return "auth/registration";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute CustomerCreateRequest customerCreateRequest) {
        customerService.save(customerCreateRequest);
        return "redirect:/home";
    }

}
