package ru.randomplay.musicshop.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateRequest {
    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 32, message = "Last name must have from {min} to {max} characters")
    private String lastName;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 32, message = "First name must have from {min} to {max} characters")
    private String firstName;

    private String phone;
}
