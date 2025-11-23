package ru.randomplay.musicshop.dto;

import lombok.Getter;
import lombok.Setter;
import ru.randomplay.musicshop.model.WorkerStatus;

@Getter
@Setter
public class EmployeeCreateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private WorkerStatus status;
}
