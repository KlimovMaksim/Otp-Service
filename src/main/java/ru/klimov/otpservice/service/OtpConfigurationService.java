package ru.klimov.otpservice.service;

import ru.klimov.otpservice.entity.OtpConfiguration;

public interface OtpConfigurationService {

    OtpConfiguration saveOrUpdateConfiguration(int otpLength, int otpExpirationMinutes);

    OtpConfiguration getConfiguration();
}
