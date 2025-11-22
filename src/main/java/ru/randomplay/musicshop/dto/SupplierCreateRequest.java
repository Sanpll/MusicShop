package ru.randomplay.musicshop.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class SupplierCreateRequest {
    private String name;
    private String country;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate contractStart;
    @DateTimeFormat(pattern = "dd.MM.yyyy")
    private LocalDate contractEnd;
}
