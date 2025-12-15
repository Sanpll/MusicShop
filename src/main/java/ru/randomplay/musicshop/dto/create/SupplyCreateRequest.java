package ru.randomplay.musicshop.dto.create;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class SupplyCreateRequest {
    @NotNull(message = "Products map is required")
    @NotEmpty(message = "Products map must not be empty")
    private Map<@NotNull(message = "Product ID is required") @Positive(message = "Product ID must be positive") Long, @NotNull(message = "Product quantity is required") @Min(value = 1, message = "Product quantity must be at least {value}") Integer> productsId;
}
