package ru.randomplay.musicshop.dto.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeCreateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Long storeId;
}
