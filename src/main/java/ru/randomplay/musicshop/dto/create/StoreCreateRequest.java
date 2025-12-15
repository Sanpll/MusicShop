package ru.randomplay.musicshop.dto.create;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.randomplay.musicshop.model.StoreStatus;

import java.time.LocalDate;

@Getter
@Setter
public class StoreCreateRequest {
    @NotNull(message = "Store status is required")
    private StoreStatus status;

    @NotBlank(message = "Location is required")
    @Size(min = 10, max = 64, message = "Location must have from {min} to {max} characters")
    private String location;

    @NotBlank(message = "Working hours are required")
    @Size(min = 6, max = 32, message = "Working hours must have from {min} to {max} characters")
    private String workingHours;

    @NotNull(message = "Open date is required")
    @PastOrPresent(message = "Open date must be in the past or present")
    private LocalDate openDate;
}
