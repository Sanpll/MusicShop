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
import ru.randomplay.musicshop.dto.update.ProductUpdateRequest;
import ru.randomplay.musicshop.dto.update.SupplierUpdateRequest;
import ru.randomplay.musicshop.service.CategoryService;
import ru.randomplay.musicshop.service.ProductService;
import ru.randomplay.musicshop.service.SupplierService;

@Controller
@RequestMapping("/warehouse-manager")
@PreAuthorize("hasRole('WAREHOUSE_MANAGER')")
@RequiredArgsConstructor
public class WarehouseManagerController {
    private final SupplierService supplierService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/dashboard")
    public String dashboard(Model model,
                            @RequestParam(required = false) String table) {
        if (table == null) {
            model.addAttribute("supplies", null/*supplyService.getAll()*/);
        } else {
            switch (table) {
                case "suppliers":
                    model.addAttribute("suppliers", supplierService.getAll());
                    break;
                case "products":
                    model.addAttribute("products", productService.getAll());
                    break;
                default:
                    model.addAttribute("supplies", null/*supplyService.getAll()*/);
                    break;
            }
        }
        return "warehouse/dashboard";
    }

    @GetMapping("/add/supplier")
    public String newSupplierPage() {
        return "warehouse/newSupplier";
    }

    @GetMapping("/add/product")
    public String newProductPage(Model model,
                                 @RequestParam(required = false) String error) {
        model.addAttribute("error", error);
        model.addAttribute("suppliers", supplierService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "warehouse/newProduct";
    }

    @GetMapping("/update/supplier/{id}")
    public String updateSupplierPage(Model model,
                                     @PathVariable Long id) {
        model.addAttribute("supplier", supplierService.get(id));
        return "warehouse/updateSupplier";
    }

    @GetMapping("/update/product/{id}")
    public String updateProductPage(Model model,
                                    @PathVariable Long id,
                                    @RequestParam(required = false) String error) {
        model.addAttribute("error", error);
        model.addAttribute("product", productService.get(id));
        model.addAttribute("suppliers", supplierService.getAll());
        model.addAttribute("categories", categoryService.getAll());
        return "warehouse/updateProduct";
    }

    @PostMapping("/add/supplier")
    public String newSupplier(@Valid @ModelAttribute SupplierCreateRequest supplierCreateRequest) {
        supplierService.save(supplierCreateRequest);
        return "redirect:/warehouse-manager/dashboard?table=suppliers";
    }

    @PostMapping("/add/product")
    public String newProduct(@Valid @ModelAttribute ProductCreateRequest productCreateRequest,
                             @RequestParam("image") MultipartFile image) {
        if (productCreateRequest.getCategoryIds() == null) {
            return "redirect:/warehouse-manager/add/product?error=true";
        }
        productService.save(productCreateRequest, image);
        return "redirect:/warehouse-manager/dashboard?table=products";
    }

    @PostMapping("/update/supplier/{id}")
    public String updateSupplier(@PathVariable Long id,
                                 @Valid @ModelAttribute SupplierUpdateRequest supplierUpdateRequest) {
        supplierService.update(id, supplierUpdateRequest);
        return "redirect:/warehouse-manager/dashboard?table=suppliers";
    }

    @PostMapping("/update/product/{id}")
    public String updateProduct(@PathVariable Long id,
                                @Valid @ModelAttribute ProductUpdateRequest productUpdateRequest,
                                @RequestParam("image") MultipartFile image) {
        if (productUpdateRequest.getCategoryIds() == null) {
            return "redirect:/warehouse-manager/update/product/%d?error=true".formatted(id);
        }
        productService.update(id, productUpdateRequest, image);
        return "redirect:/warehouse-manager/dashboard?table=products";
    }
}
