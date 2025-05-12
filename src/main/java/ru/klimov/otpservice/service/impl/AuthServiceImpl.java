package ru.klimov.otpservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.klimov.otpservice.dto.request.UserRequestDto;
import ru.klimov.otpservice.dto.request.UserShortRequestDto;
import ru.klimov.otpservice.dto.response.TokenResponseDto;
import ru.klimov.otpservice.dto.response.UserResponseDto;
import ru.klimov.otpservice.entity.User;
import ru.klimov.otpservice.entity.enums.Role;
import ru.klimov.otpservice.exception.RegistrationException;
import ru.klimov.otpservice.exception.UserNotFoundException;
import ru.klimov.otpservice.mapper.UserMapper;
import ru.klimov.otpservice.repository.UserRepository;
import ru.klimov.otpservice.secutity.JwtUtil;
import ru.klimov.otpservice.service.AuthService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper, JwtUtil jwtUtil,
                           PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UserResponseDto register(UserRequestDto userRequestDto) {
        log.info("Starting registration process for user: {}", userRequestDto.getLogin());
        String role = userRequestDto.getRole();

        if (!(role.equals(Role.ADMIN.toString()) || role.equals(Role.USER.toString()))) {
            log.error("Invalid role provided: {}", role);
            throw new RegistrationException("Only one of two roles is available for installation: "
                    + Role.ADMIN + " or " + Role.USER + ".");
        }

        if (role.equals(Role.ADMIN.toString())) {
            List<User> admin = userRepository.findAllByRole(Role.ADMIN);
            if (!admin.isEmpty()) {
                log.error("Attempt to register second administrator account");
                throw new RegistrationException("Only one administrator can be installed.");
            }
        }

        User newUser = userMapper.userRequestDtoToUser(userRequestDto);
        User savedUser = userRepository.save(newUser);
        log.info("Successfully registered new user with login: {}", savedUser.getLogin());

        return userMapper.userToUserResponseDto(savedUser);
    }

    @Override
    public TokenResponseDto login(UserShortRequestDto userShortRequestDto) {
        log.info("Processing login request for user: {}", userShortRequestDto.getLogin());
        Optional<User> optionalUser = userRepository.findByLogin(userShortRequestDto.getLogin());

        if (optionalUser.isEmpty() || !passwordEncoder.matches(userShortRequestDto.getPassword(),
                optionalUser.get().getPassword())) {
            log.error("Failed login attempt for user: {}", userShortRequestDto.getLogin());
            throw new UserNotFoundException("The user with the specified data was not found.");
        }

        String token = jwtUtil.generateTokenForUser(userShortRequestDto.getLogin());
        log.info("Successfully generated token for user: {}", userShortRequestDto.getLogin());
        return TokenResponseDto.builder()
                .token(token)
                .build();
    }
}
