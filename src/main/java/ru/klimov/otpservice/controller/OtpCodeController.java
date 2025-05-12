package ru.klimov.otpservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.klimov.otpservice.dto.response.OtpCodeResponse;
import ru.klimov.otpservice.service.OtpCodeService;

@Slf4j
@RestController
@RequestMapping("/otpCode")
@RequiredArgsConstructor
public class OtpCodeController {

    private final OtpCodeService otpCodeService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<OtpCodeResponse> generateOtpCode() {
        log.info("Received request to generate OTP code");
        OtpCodeResponse response = otpCodeService.generateOtpCode();
        log.info("Successfully generated OTP code");
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{code}/verify")
    public ResponseEntity<Boolean> testGenerateOtpCode(@PathVariable String code) {
        log.info("Received request to verify OTP code: {}", code);
        Boolean result = otpCodeService.verifyCode(code);
        log.info("OTP code verification result: {}", result);
        return ResponseEntity.ok(result);
    }
}
