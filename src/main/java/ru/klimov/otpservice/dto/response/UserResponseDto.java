package ru.klimov.otpservice.dto.response;

import lombok.Data;

import java.util.UUID;

@Data
public class UserResponseDto {

    private UUID id;
    private String login;
    private String role;
}
