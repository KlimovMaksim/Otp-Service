package ru.klimov.otpservice;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import ru.klimov.otpservice.entity.User;
import ru.klimov.otpservice.entity.enums.Role;
import ru.klimov.otpservice.repository.UserRepository;

@SpringBootApplication
public class OtpServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OtpServiceApplication.class, args);
    }
}
