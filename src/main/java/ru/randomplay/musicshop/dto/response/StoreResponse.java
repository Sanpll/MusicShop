package ru.randomplay.musicshop.dto.response;

import lombok.Getter;
import lombok.Setter;
import ru.randomplay.musicshop.model.StoreStatus;

import java.time.LocalDate;

@Getter
@Setter
public class StoreResponse {
    private Long id;
    private StoreStatus status;
    private String location;
    private String workingHours;
    private LocalDate openDate;
}
