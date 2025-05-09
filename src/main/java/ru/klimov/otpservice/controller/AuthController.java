package ru.klimov.otpservice.controller;

import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PreAuthorize("permitAll()")
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserRequestDto userRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(userRequestDto));
    }

    @PreAuthorize("permitAll()")
    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody UserShortRequestDto userShortRequestDto) {
        return ResponseEntity.ok(authService.login(userShortRequestDto));
    }
}
