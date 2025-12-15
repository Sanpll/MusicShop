package ru.randomplay.musicshop.dto.update;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import ru.randomplay.musicshop.model.ProductStatus;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProductUpdateRequest {
    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 32, message = "Product name must have from {min} to {max} characters")
    private String name;

    @Size(max = 256, message = "Product description must have up to {max} characters")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than {value}")
    @Digits(integer = 8, fraction = 2, message = "Price must have up to {integer} integer digits and {fraction} fraction digits")
    private BigDecimal price;

    @NotNull(message = "Product status is required")
    private ProductStatus status;

    private List<Long> categoryIds;

    private String imageFilename;
}
