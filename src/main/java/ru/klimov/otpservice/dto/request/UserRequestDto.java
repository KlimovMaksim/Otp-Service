package ru.klimov.otpservice.dto.request;

import lombok.Data;

@Data
public class UserRequestDto {

    private String login;
    private String password;
    private String role;
}