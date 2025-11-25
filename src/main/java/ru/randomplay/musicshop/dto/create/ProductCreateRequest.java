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
    @NotNull
    private Long supplierId;

    @NotBlank
    @Size(min = 2, max = 32)
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 8, fraction = 2)
    private BigDecimal price;

    @NotNull
    @Min(value = 0)
    @Max(value = 100000)
    private Integer quantity;

    @NotNull
    private ProductStatus status;

    private List<Long> categoryIds;

    private String imageFilename;
}
