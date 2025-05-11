package ru.klimov.otpservice.service;

import ru.klimov.otpservice.dto.request.OtpConfigurationRequestDto;
import ru.klimov.otpservice.dto.response.OtpConfigurationResponseDto;
import ru.klimov.otpservice.entity.OtpConfiguration;

public interface OtpConfigurationService {

    OtpConfigurationResponseDto saveOrUpdateConfiguration(OtpConfigurationRequestDto otpConfigurationRequestDto);

    OtpConfiguration saveOrUpdateConfiguration(int otpLength, int otpExpirationMinutes);

    OtpConfiguration getConfiguration();
}
