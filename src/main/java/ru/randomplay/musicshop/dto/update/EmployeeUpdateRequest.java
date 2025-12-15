package ru.randomplay.musicshop.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.randomplay.musicshop.model.UserActivity;
import ru.randomplay.musicshop.model.WorkerStatus;

@Getter
@Setter
public class EmployeeUpdateRequest {
    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 32, message = "First name must have from {min} to {max} characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 32, message = "Last name must have from {min} to {max} characters")
    private String lastName;

    private String phone;

    @NotNull(message = "User activity is required")
    private UserActivity activity;

    @NotNull(message = "Worker status is required")
    private WorkerStatus status;

    @NotNull(message = "Store ID is required")
    private Long storeId;
}
