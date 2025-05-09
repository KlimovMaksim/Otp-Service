package ru.klimov.otpservice.service.impl;

import lombok.RequiredArgsConstructor;
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

import java.math.BigDecimal;
import java.util.Optional;

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
        String role = userRequestDto.getRole();

        if (!(role.equals(Role.ADMIN.toString()) || role.equals(Role.USER.toString()))) {
            throw new RegistrationException("Only one of two roles is available for installation: "
                    + Role.ADMIN + " or " + Role.USER + ".");
        }

        User newUser = userMapper.userRequestDtoToUser(userRequestDto);

        return userMapper.userToUserResponseDto(userRepository.save(newUser));
    }

    @Override
    public TokenResponseDto login(UserShortRequestDto userShortRequestDto) {
        Optional<User> optionalUser = userRepository.findByLogin(userShortRequestDto.getLogin());

        if (optionalUser.isEmpty() || !passwordEncoder.matches(userShortRequestDto.getPassword(),
                optionalUser.get().getPassword())) {

            throw new UserNotFoundException("The user with the specified data was not found.");
        }

        return TokenResponseDto.builder()
                .token(jwtUtil.generateTokenForUser(userShortRequestDto.getLogin()))
                .build();
    }
}
