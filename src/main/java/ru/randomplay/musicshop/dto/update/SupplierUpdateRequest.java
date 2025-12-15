package ru.randomplay.musicshop.dto.update;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SupplierUpdateRequest {
    @NotBlank(message = "Supplier name is required")
    @Size(min = 3, max = 32, message = "Supplier name must have from {min} to {max} characters")
    private String name;

    @NotBlank(message = "Country is required")
    @Size(min = 3, max = 32, message = "Country must have from {min} to {max} characters")
    private String country;

    @NotNull(message = "Contract start date is required")
    @PastOrPresent(message = "Contract start date must be in the past or present")
    private LocalDate contractStart;

    @NotNull(message = "Contract end date is required")
    @Future(message = "Contract end date must be in the future")
    private LocalDate contractEnd;
}

