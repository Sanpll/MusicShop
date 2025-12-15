package ru.randomplay.musicshop.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryUpdateRequest {
    @NotBlank(message = "Category name is required")
    @Size(min = 3, max = 32, message = "Category name must have from {min} to {max} characters")
    private String name;

    @Size(max = 256, message = "Category description must have up to {max} characters")
    private String description;
}
