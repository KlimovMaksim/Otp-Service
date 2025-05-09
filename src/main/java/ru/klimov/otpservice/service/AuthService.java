package ru.klimov.otpservice.service;

import ru.klimov.otpservice.dto.request.UserRequestDto;
import ru.klimov.otpservice.dto.request.UserShortRequestDto;
import ru.klimov.otpservice.dto.response.TokenResponseDto;
import ru.klimov.otpservice.dto.response.UserResponseDto;

public interface AuthService {

    UserResponseDto register(UserRequestDto userRequestDto);

    TokenResponseDto login(UserShortRequestDto userShortRequestDto);
}
