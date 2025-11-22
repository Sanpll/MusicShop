package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
