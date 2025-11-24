package ru.randomplay.musicshop.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AdminResponse {
    private String email;
    private String activity;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime registeredAt;
}
