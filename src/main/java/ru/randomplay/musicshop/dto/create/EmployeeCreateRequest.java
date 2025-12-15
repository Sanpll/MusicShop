package ru.randomplay.musicshop.dto.create;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCreateRequest {
    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 32, message = "Password must have from {min} to {max} characters")
    private String password;

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 32, message = "First name must have from {min} to {max} characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 32, message = "Last name must have from {min} to {max} characters")
    private String lastName;

    @Pattern(regexp = "^(|\\+7\\d{10}|8\\d{10})$", message = "Phone number must be empty, start with +7 followed by 10 digits, or start with 8 followed by 10 digits")
    private String phone;

    @NotNull(message = "Store ID is required")
    private Long storeId;
}
