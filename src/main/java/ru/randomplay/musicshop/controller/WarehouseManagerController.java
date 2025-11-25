package ru.randomplay.musicshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.dto.create.SupplierCreateRequest;
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
    public String newProductPage(Model model) {
        model.addAttribute("suppliers", supplierService.getAll());
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
        String directoryName = "/musicshop/images/products/";
        String originalFilename = image.getOriginalFilename();
        String fileName = null;
        System.out.println(directoryName);
        System.out.println(originalFilename);

        // можно сохранить товар без фотки
        if (originalFilename != null) {
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            fileName = productCreateRequest.getName() + "_" + UUID.randomUUID() + fileExtension;
            System.out.println(fileExtension);
            System.out.println(fileName);

            try {
                image.transferTo(new File(directoryName + fileName));
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image", e);
            }
        }

        // т.к. все наименования товаров уникальны, то фотка будет называться также
        productCreateRequest.setImageFilename(fileName);
        productService.save(productCreateRequest);
        return "redirect:/warehouse-manager/suppliers";
    }
}
