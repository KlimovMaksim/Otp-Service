package ru.klimov.otpservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.klimov.otpservice.dto.response.UserResponseDto;
import ru.klimov.otpservice.entity.User;
import ru.klimov.otpservice.entity.enums.Role;
import ru.klimov.otpservice.mapper.UserMapper;
import ru.klimov.otpservice.repository.UserRepository;
import ru.klimov.otpservice.service.UserService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getUsers() {
        List<User> users = userRepository.findAllByRole(Role.USER);
        return users.stream().map(userMapper::userToUserResponseDto).toList();
    }

    @Override
    public void delete(String id) {
        userRepository.deleteById(UUID.fromString(id));
    }
}
