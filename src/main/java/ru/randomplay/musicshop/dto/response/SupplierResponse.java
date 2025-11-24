package ru.randomplay.musicshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SupplierResponse {
    private String name;
    private String country;
    private LocalDate contractStart;
    private LocalDate contractEnd;
}
