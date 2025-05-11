package ru.klimov.otpservice.service;


import ru.klimov.otpservice.dto.response.UserResponseDto;

import java.util.List;

public interface UserService {

    List<UserResponseDto> getUsers();

    void delete(String id);
}
