package ru.klimov.otpservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klimov.otpservice.dto.request.OtpConfigurationRequestDto;
import ru.klimov.otpservice.dto.response.OtpConfigurationResponseDto;
import ru.klimov.otpservice.service.OtpConfigurationService;

@Slf4j
@RestController
@RequestMapping("/otpConfiguration")
@RequiredArgsConstructor
public class OtpConfigurationController {

    private final OtpConfigurationService otpConfigurationService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<OtpConfigurationResponseDto> updateOtpConfiguration(@RequestBody OtpConfigurationRequestDto requestDto) {
        log.info("Received request to update OTP configuration: {}", requestDto);
        OtpConfigurationResponseDto response = otpConfigurationService.saveOrUpdateConfiguration(requestDto);
        log.info("Successfully updated OTP configuration: {}", response);
        return ResponseEntity.ok(response);
    }
}
