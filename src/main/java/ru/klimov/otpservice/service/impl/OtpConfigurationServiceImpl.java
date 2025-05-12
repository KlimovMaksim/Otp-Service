package ru.klimov.otpservice.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.klimov.otpservice.dto.request.OtpConfigurationRequestDto;
import ru.klimov.otpservice.dto.response.OtpConfigurationResponseDto;
import ru.klimov.otpservice.entity.OtpConfiguration;
import ru.klimov.otpservice.mapper.OtpConfigurationMapper;
import ru.klimov.otpservice.repository.OtpConfigurationRepository;
import ru.klimov.otpservice.service.OtpConfigurationService;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpConfigurationServiceImpl implements OtpConfigurationService {

    private final int DEFAULT_OTP_LENGTH = 5;
    private final int DEFAULT_OTP_EXPIRATION_MINUTES = 5;

    private final OtpConfigurationRepository repository;
    private final OtpConfigurationMapper otpConfigurationMapper;

    @Override
    public OtpConfigurationResponseDto saveOrUpdateConfiguration(OtpConfigurationRequestDto requestDto) {
        OtpConfiguration otpConfiguration = saveOrUpdateConfiguration(requestDto.getOtpLength(), requestDto.getOtpExpirationMinutes());
        return otpConfigurationMapper.toOtpConfigurationResponseDto(otpConfiguration);
    }

    @Override
    public OtpConfiguration saveOrUpdateConfiguration(int otpLength, int otpExpirationMinutes) {
        log.info("Saving OTP configuration with length: {} and expiration: {} minutes", otpLength, otpExpirationMinutes);
        OtpConfiguration config = repository.getSingletonConfig()
                .orElse(new OtpConfiguration());

        config.setOtpLength(otpLength);
        config.setOtpExpirationMinutes(otpExpirationMinutes);

        OtpConfiguration savedConfig = repository.save(config);
        log.info("Successfully saved OTP configuration: {}", savedConfig);
        return savedConfig;
    }

    @Override
    public OtpConfiguration getConfiguration() {
        log.debug("Retrieving OTP configuration");
        OtpConfiguration config = repository.getSingletonConfig()
                .orElseThrow(() -> new IllegalStateException("OTP Configuration not initialized"));
        log.debug("Retrieved OTP configuration: {}", config);
        return config;
    }

    @PostConstruct
    private void setDefaultConfiguration() {
        log.info("Setting default OTP configuration with length: {} and expiration: {} minutes",
                DEFAULT_OTP_LENGTH, DEFAULT_OTP_EXPIRATION_MINUTES);
        saveOrUpdateConfiguration(DEFAULT_OTP_LENGTH, DEFAULT_OTP_EXPIRATION_MINUTES);
    }
}
