package ru.randomplay.musicshop.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerCreateRequest {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
}
