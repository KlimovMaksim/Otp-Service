package ru.klimov.otpservice.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OtpCodeResponse {

    private String code;
    private LocalDateTime expiryDate;
}
