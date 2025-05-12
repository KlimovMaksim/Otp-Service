package ru.klimov.otpservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klimov.otpservice.dto.request.UserRequestDto;
import ru.klimov.otpservice.dto.request.UserShortRequestDto;
import ru.klimov.otpservice.dto.response.TokenResponseDto;
import ru.klimov.otpservice.dto.response.UserResponseDto;
import ru.klimov.otpservice.service.AuthService;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        log.info("Received request to register user: {}", userRequestDto);
        UserResponseDto response = authService.register(userRequestDto);
        log.info("Successfully registered user: {}", response);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserShortRequestDto userShortRequestDto) {
        log.info("Received login request for user: {}", userShortRequestDto.getLogin());
        TokenResponseDto tokenResponse = authService.login(userShortRequestDto);
        log.info("Successfully logged in user: {}, Token generated", userShortRequestDto.getLogin());
        return ResponseEntity.ok(tokenResponse);
    }
}