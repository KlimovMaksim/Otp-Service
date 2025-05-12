package ru.klimov.otpservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.klimov.otpservice.dto.response.UserResponseDto;
import ru.klimov.otpservice.entity.User;
import ru.klimov.otpservice.entity.enums.Role;
import ru.klimov.otpservice.mapper.UserMapper;
import ru.klimov.otpservice.repository.UserRepository;
import ru.klimov.otpservice.service.UserService;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getUsers() {
        log.info("Retrieving all users with role USER");
        List<User> users = userRepository.findAllByRole(Role.USER);
        log.info("Found {} users with role USER", users.size());
        return users.stream().map(userMapper::userToUserResponseDto).toList();
    }

    @Override
    public void delete(String id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(UUID.fromString(id));
        log.info("Successfully deleted user with id: {}", id);
    }
}
