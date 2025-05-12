package ru.klimov.otpservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.klimov.otpservice.entity.enums.CodeStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "otp_code")
@Data
public class OtpCode {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "expire_date", nullable = false)
    private LocalDateTime expiryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CodeStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
}
