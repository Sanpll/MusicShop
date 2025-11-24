package ru.randomplay.musicshop.dto.create;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SupplierCreateRequest {
    private String name;
    private String country;
    private LocalDate contractStart;
    private LocalDate contractEnd;
}
