package ru.randomplay.musicshop.dto.request;

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
public class StoreRequest {
    @NotNull
    private StoreStatus status;

    @NotBlank
    @Size(min = 10, max = 64)
    private String location;

    @NotBlank
    @Size(min = 6, max = 32)
    private String workingHours;

    @NotNull
    @PastOrPresent
    private LocalDate openDate;
}
