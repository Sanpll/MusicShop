package ru.randomplay.musicshop.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerUpdateRequest {
    @NotBlank
    @Size(min = 3, max = 32)
    private String lastName;

    @NotBlank
    @Size(min = 3, max = 32)
    private String firstName;

    private String phone;
}
