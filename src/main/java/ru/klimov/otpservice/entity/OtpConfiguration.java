package ru.klimov.otpservice.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "otp_configuration")
@Data
public class OtpConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "singleton_key", nullable = false, unique = true, updatable = false)
    private String singletonKey = "OTP_CONFIGURATION_SINGLETON";

    @Column(name = "otp_length", nullable = false)
    private Integer otpLength;

    @Column(name = "otp_expiration_minutes", nullable = false)
    private Integer otpExpirationMinutes;

    @PrePersist
    @PreUpdate
    private void ensureSingleton() {
        this.singletonKey = "OTP_CONFIGURATION_SINGLETON";
    }
}
