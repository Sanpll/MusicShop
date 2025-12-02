package ru.randomplay.musicshop.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.randomplay.musicshop.model.UserActivity;
import ru.randomplay.musicshop.model.WorkerStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class WarehouseManagerResponse {
    private Long id;
    private String email;
    private UserActivity activity;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime registeredAt;
    private Long storeId;
    private String storeLocation;
    private WorkerStatus status;
}
