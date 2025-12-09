package ru.randomplay.musicshop.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.randomplay.musicshop.dto.create.ProductCreateRequest;
import ru.randomplay.musicshop.dto.create.SupplierCreateRequest;
import ru.randomplay.musicshop.dto.update.ProductUpdateRequest;
import ru.randomplay.musicshop.dto.update.SupplierUpdateRequest;
import ru.randomplay.musicshop.service.CategoryService;
import ru.randomplay.musicshop.service.ProductService;
import ru.randomplay.musicshop.service.SupplierService;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

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

    private String createFilename(MultipartFile image) {
        String filename = null;
        if (!image.isEmpty()) {
            // проверка, чтобы файл имел необходимый формат (картинка)
            if (!Objects.requireNonNull(image.getContentType()).startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            // получение оригинального имени
            String originalName = Optional.ofNullable(image.getOriginalFilename())
                    .map(StringUtils::cleanPath)
                    .filter(name -> !name.isEmpty())
                    .orElse("image");

            // извлечение и валидация расширения
            String extension = Stream.of(".jpg", ".jpeg", ".png")
                    .filter(ext -> originalName.toLowerCase().endsWith(ext))
                    .findFirst()
                    .orElse(".jpg");

            filename = UUID.randomUUID() + extension;

            try {
                image.transferTo(new File(uploadDir + File.separator + filename));
            } catch (IOException e) {
                throw new RuntimeException("Failed to save image", e);
            }
        }
        return filename;
    }

    @PostMapping("/add/product")
    public String newProduct(@Valid @ModelAttribute ProductCreateRequest productCreateRequest,
                             @RequestParam("image") MultipartFile image) {
        if (productCreateRequest.getCategoryIds() == null) {
            return "redirect:/warehouse-manager/add/product?error=true";
        }

        productCreateRequest.setImageFilename(createFilename(image));
        productService.save(productCreateRequest);
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

        productUpdateRequest.setImageFilename(createFilename(image));
        productService.update(id, productUpdateRequest);
        return "redirect:/warehouse-manager/dashboard?table=products";
    }
}
