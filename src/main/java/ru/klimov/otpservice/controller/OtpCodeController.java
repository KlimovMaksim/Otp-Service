package ru.klimov.otpservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.klimov.otpservice.dto.response.OtpCodeResponse;
import ru.klimov.otpservice.service.OtpCodeService;

@RestController
@RequestMapping("/otpCode")
@RequiredArgsConstructor
public class OtpCodeController {

    private final OtpCodeService otpCodeService;

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public ResponseEntity<OtpCodeResponse> generateOtpCode() {
        return ResponseEntity.ok(otpCodeService.generateOtpCode());
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/{code}/verify")
    public ResponseEntity<Boolean> testGenerateOtpCode(@PathVariable String code) {
        return ResponseEntity.ok(otpCodeService.verifyCode(code));
    }
}
