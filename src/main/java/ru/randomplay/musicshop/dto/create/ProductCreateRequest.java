package ru.randomplay.musicshop.dto.create;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import ru.randomplay.musicshop.model.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductCreateRequest {
    @NotNull(message = "Supplier ID is required")
    private Long supplierId;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 32, message = "Product name must have from {min} to {max} characters")
    private String name;

    @Size(max = 256, message = "Product description must have up to {max} characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than {value}")
    @Digits(integer = 8, fraction = 2, message = "Price must have up to {integer} integer digits and {fraction} fraction digits")
    private BigDecimal price;

    @NotNull(message = "Quantity is required")
    @Min(value = 0, message = "Quantity must be at least {value}")
    @Max(value = 100000, message = "Quantity must be at most {value}")
    private Integer quantity;

    @NotNull(message = "Product status is required")
    private ProductStatus status;

    private List<Long> categoryIds;

    private String imageFilename;
}
