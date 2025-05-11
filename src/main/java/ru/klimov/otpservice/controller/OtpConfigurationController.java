package ru.klimov.otpservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klimov.otpservice.dto.request.OtpConfigurationRequestDto;
import ru.klimov.otpservice.dto.response.OtpConfigurationResponseDto;
import ru.klimov.otpservice.service.OtpConfigurationService;

@RestController
@RequestMapping("/otpConfiguration")
@RequiredArgsConstructor
public class OtpConfigurationController {

    private final OtpConfigurationService otpConfigurationService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping
    public ResponseEntity<OtpConfigurationResponseDto> updateOtpConfiguration(@RequestBody OtpConfigurationRequestDto requestDto) {
        return ResponseEntity.ok(otpConfigurationService.saveOrUpdateConfiguration(requestDto));
    }
}
