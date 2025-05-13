package ru.klimov.otpservice.service;

public interface EmailService {

    void sendEmail(String to, String subject, String body);
}
