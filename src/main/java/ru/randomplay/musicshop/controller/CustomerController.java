package ru.randomplay.musicshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerController {
    @GetMapping("/home")
    public String home(Model model) {
        return "customer/home";
    }
}
