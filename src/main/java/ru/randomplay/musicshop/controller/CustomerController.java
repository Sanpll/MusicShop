package ru.randomplay.musicshop.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.randomplay.musicshop.entity.Customer;
import ru.randomplay.musicshop.entity.User;
import ru.randomplay.musicshop.service.CartService;
import ru.randomplay.musicshop.service.CustomerService;
import ru.randomplay.musicshop.service.ProductService;

@Controller
@PreAuthorize("hasRole('CUSTOMER')")
@RequiredArgsConstructor
public class CustomerController {
    private final ProductService productService;
    private final CustomerService customerService;
    private final CartService cartService;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("products", productService.getAll());
        return "customer/home";
    }

    @GetMapping("/product/{id}")
    public String productPage(Model model,
                              @PathVariable Long id) {
        model.addAttribute("product", productService.get(id));
        return "customer/product";
    }

    @GetMapping("/cart")
    public String cartPage(Model model,
                           @AuthenticationPrincipal User user) {
        Customer customer = customerService.findByUserEmail(user.getEmail());
        model.addAttribute("cartItems", cartService.getAll(customer.getCart()));
        return "customer/cart";
    }

    @GetMapping("/check-order")
    public String checkOrderPage(Model model,
                                 @AuthenticationPrincipal User user) {
        Customer customer = customerService.findByUserEmail(user.getEmail());
        model.addAttribute("cartItems", cartService.getAll(customer.getCart()));
        model.addAttribute("totalPrice", cartService.getTotalPrice(customer.getCart()));
        return "/customer/checkOrder";
    }

    @PostMapping("/add/product")
    public String addProductToCart(@RequestParam Long productId,
                                   @AuthenticationPrincipal User user) {
        Customer customer = customerService.findByUserEmail(user.getEmail());
        cartService.addProduct(customer.getCart(), productId, 1);
        return "redirect:/home";
    }

    @PostMapping("/delete/product")
    public String deleteProductFromCart(@RequestParam Long productId,
                                        @AuthenticationPrincipal User user) {
        Customer customer = customerService.findByUserEmail(user.getEmail());
        cartService.deleteProduct(customer.getCart(), productId);
        return "redirect:/cart";
    }

    @PostMapping("/create/order")
    public String createOrder(@AuthenticationPrincipal User user) {
        Customer customer = customerService.findByUserEmail(user.getEmail());

        return "";
    }
}
