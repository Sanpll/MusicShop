package ru.randomplay.musicshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserCreateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}
