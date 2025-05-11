package ru.klimov.otpservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import ru.klimov.otpservice.entity.enums.Role;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "otp_user")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "login", unique = true, nullable = false)
    private String login;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OtpCode> otpCodes;
}