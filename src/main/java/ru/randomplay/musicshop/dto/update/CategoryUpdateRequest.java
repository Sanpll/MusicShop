package ru.randomplay.musicshop.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequest {
    @NotBlank
    @Size(min = 3, max = 32)
    private String name;

    @Size(min = 4, max = 256)
    private String description;
}
