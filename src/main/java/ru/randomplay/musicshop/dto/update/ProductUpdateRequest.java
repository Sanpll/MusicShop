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
    @NotBlank
    @Size(min = 2, max = 32)
    private String name;

    @Size(min = 4, max = 256)
    private String description;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull
    private ProductStatus status;

    private List<Long> categoryIds;

    private String imageFilename;
}
