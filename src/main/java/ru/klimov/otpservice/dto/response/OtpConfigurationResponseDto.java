package ru.klimov.otpservice.dto.response;

import lombok.Data;

@Data
public class OtpConfigurationResponseDto {

    private Integer otpLength;
    private Integer otpExpirationMinutes;
}
