package ru.klimov.otpservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klimov.otpservice.entity.OtpConfiguration;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpConfigurationRepository extends JpaRepository<OtpConfiguration, UUID> {

    Optional<OtpConfiguration> findBySingletonKey(String singletonKey);

    default Optional<OtpConfiguration> getSingletonConfig() {
        return findBySingletonKey("OTP_CONFIGURATION_SINGLETON");
    }
}
