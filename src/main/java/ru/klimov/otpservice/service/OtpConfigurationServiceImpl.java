package ru.klimov.otpservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klimov.otpservice.entity.OtpConfiguration;
import ru.klimov.otpservice.repository.OtpConfigurationRepository;

@Service
@RequiredArgsConstructor
public class OtpConfigurationServiceImpl implements  OtpConfigurationService {

    private final int DEFAULT_OTP_LENGTH = 5;
    private final int DEFAULT_OTP_EXPIRATION_MINUTES = 5;

    private final OtpConfigurationRepository repository;

    @Transactional
    public OtpConfiguration saveOrUpdateConfiguration(int otpLength, int otpExpirationMinutes) {
        OtpConfiguration config = repository.getSingletonConfig()
                .orElse(new OtpConfiguration());

        config.setOtpLength(otpLength);
        config.setOtpExpirationMinutes(otpExpirationMinutes);

        return repository.save(config);
    }

    @Transactional(readOnly = true)
    public OtpConfiguration getConfiguration() {
        return repository.getSingletonConfig()
                .orElseThrow(() -> new IllegalStateException("OTP Configuration not initialized"));
    }

    @PostConstruct
    private void setDefaultConfiguration() {
        saveOrUpdateConfiguration(DEFAULT_OTP_LENGTH, DEFAULT_OTP_EXPIRATION_MINUTES);
    }
}
