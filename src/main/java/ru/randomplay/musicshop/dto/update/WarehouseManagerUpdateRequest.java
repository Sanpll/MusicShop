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
public class WarehouseManagerUpdateRequest {
    @NotBlank
    @Size(min = 3, max = 32)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 32)
    private String lastName;

    private String phone;

    @NotNull
    private UserActivity activity;

    @NotNull
    private WorkerStatus status;

    @NotNull
    private Long storeId;
}
