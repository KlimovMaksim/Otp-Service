package ru.klimov.otpservice.service;

import ru.klimov.otpservice.dto.response.OtpCodeResponse;

public interface OtpCodeService {

    OtpCodeResponse generateOtpCode();

    Boolean verifyCode(String code);
}
