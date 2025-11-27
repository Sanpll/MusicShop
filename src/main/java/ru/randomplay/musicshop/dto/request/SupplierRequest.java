package ru.randomplay.musicshop.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SupplierRequest {
    @NotBlank
    @Size(min = 4, max = 32)
    private String name;

    @NotBlank
    @Size(min = 3, max = 32)
    private String country;

    @NotNull
    @PastOrPresent
    private LocalDate contractStart;

    @NotNull
    @Future
    private LocalDate contractEnd;
}
