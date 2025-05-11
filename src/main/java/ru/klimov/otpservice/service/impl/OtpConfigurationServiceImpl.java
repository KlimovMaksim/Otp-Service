package ru.klimov.otpservice.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klimov.otpservice.dto.request.OtpConfigurationRequestDto;
import ru.klimov.otpservice.dto.response.OtpConfigurationResponseDto;
import ru.klimov.otpservice.entity.OtpConfiguration;
import ru.klimov.otpservice.mapper.OtpConfigurationMapper;
import ru.klimov.otpservice.repository.OtpConfigurationRepository;
import ru.klimov.otpservice.service.OtpConfigurationService;

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
        OtpConfiguration config = repository.getSingletonConfig()
                .orElse(new OtpConfiguration());

        config.setOtpLength(otpLength);
        config.setOtpExpirationMinutes(otpExpirationMinutes);

        return repository.save(config);
    }

    @Override
    public OtpConfiguration getConfiguration() {
        return repository.getSingletonConfig()
                .orElseThrow(() -> new IllegalStateException("OTP Configuration not initialized"));
    }

    @PostConstruct
    private void setDefaultConfiguration() {
        saveOrUpdateConfiguration(DEFAULT_OTP_LENGTH, DEFAULT_OTP_EXPIRATION_MINUTES);
    }
}
