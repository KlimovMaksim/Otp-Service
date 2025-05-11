package ru.klimov.otpservice.dto.request;

import lombok.Data;

@Data
public class OtpConfigurationRequestDto {

    private Integer otpLength;
    private Integer otpExpirationMinutes;
}
