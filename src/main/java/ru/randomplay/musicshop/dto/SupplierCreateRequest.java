package ru.randomplay.musicshop.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class SupplierCreateRequest {
    private String name;
    private String country;
    private LocalDate contractStart;
    private LocalDate contractEnd;
}
