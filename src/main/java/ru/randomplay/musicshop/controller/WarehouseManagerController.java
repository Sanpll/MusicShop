package ru.randomplay.musicshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.dto.create.SupplierCreateRequest;
import ru.randomplay.musicshop.service.CategoryService;
import ru.randomplay.musicshop.service.ProductService;
import ru.randomplay.musicshop.service.SupplierService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/warehouse-manager")
@PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
@RequiredArgsConstructor
public class WarehouseManagerController {
    private final SupplierService supplierService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @Value("${app.image.upload-dir}")
    private String uploadDir;

    @GetMapping("/suppliers")
    public String suppliersPage(Model model) {
        model.addAttribute("suppliers", supplierService.getAll());
        return "warehouse/suppliers";
    }

    @GetMapping("/add/supplier")
    public String newSupplierPage() {
        return "warehouse/newSupplier";
    }

    @GetMapping("/add/product")
    public String newProductPage(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("error", error);
        model.addAttribute("suppliers", supplierService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "warehouse/newProduct";
    }

    @PostMapping("/add/supplier")
    public String newSupplier(@Valid @ModelAttribute SupplierCreateRequest supplierCreateRequest) {
        supplierService.save(supplierCreateRequest);
        return "redirect:/warehouse-manager/suppliers";
    }

    @PostMapping("/add/product")
    public String newProduct(@Valid @ModelAttribute ProductCreateRequest productCreateRequest,
                             @RequestParam("image") MultipartFile image) {
        if (productCreateRequest.getCategoryIds() == null) {
            return "redirect:/warehouse-manager/add/product?error=true";
        }

        String fileName = null;

        // можно сохранить товар без фотки
        if (!image.isEmpty()) {
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            String originalFilename = image.getOriginalFilename();
            String extension = originalFilename != null ? originalFilename.substring(originalFilename.lastIndexOf(".")) : ".jpg";
            fileName = UUID.randomUUID() + extension;

            try {
                image.transferTo(new File(uploadDir + File.separator + fileName));
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image", e);
            }
        }

        productCreateRequest.setImageFilename(fileName);
        productService.save(productCreateRequest);
        return "redirect:/warehouse-manager/suppliers";
    }
}
