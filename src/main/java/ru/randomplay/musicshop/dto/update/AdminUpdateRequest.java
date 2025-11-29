package ru.randomplay.musicshop.dto.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.randomplay.musicshop.model.UserActivity;

@Getter
@Setter
public class AdminUpdateRequest {
    @NotNull
    private UserActivity activity;

    @NotBlank
    @Size(min = 3, max = 32)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 32)
    private String lastName;

    private String phone;
}
