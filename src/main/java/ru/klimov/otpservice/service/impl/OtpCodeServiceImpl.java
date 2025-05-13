package ru.klimov.otpservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.klimov.otpservice.dto.response.OtpCodeResponse;
import ru.klimov.otpservice.entity.OtpCode;
import ru.klimov.otpservice.entity.User;
import ru.klimov.otpservice.entity.enums.CodeStatus;
import ru.klimov.otpservice.mapper.OtpCodeMapper;
import ru.klimov.otpservice.repository.OtpCodeRepository;
import ru.klimov.otpservice.secutity.AuthUser;
import ru.klimov.otpservice.service.FileService;
import ru.klimov.otpservice.service.OtpCodeService;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpCodeServiceImpl implements OtpCodeService {

    private final OtpConfigurationServiceImpl otpConfigurationService;
    private final OtpCodeRepository otpCodeRepository;
    private final OtpCodeMapper otpCodeMapper;
    private final SecureRandom secureRandom = new SecureRandom();
    private final FileService fileService;

    private final char[] CHAR_ARRAY = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

    @Override
    public OtpCodeResponse generateOtpCode() {
        log.info("Starting OTP code generation");
        int length = otpConfigurationService.getConfiguration().getOtpLength();
        int expirationMinutes = otpConfigurationService.getConfiguration().getOtpExpirationMinutes();
        log.debug("Using configuration: length={}, expirationMinutes={}", length, expirationMinutes);

        String code = processCode(length);

        User user = getAuthUser();
        OtpCode otpCode = new OtpCode();
        otpCode.setCode(code);
        otpCode.setExpiryDate(LocalDateTime.now().plusMinutes(expirationMinutes));
        otpCode.setStatus(CodeStatus.ACTIVE);
        otpCode.setUser(user);

        otpCodeRepository.save(otpCode);
        fileService.writeToFile(String.format("User: %s, OTP code: %s", user.getLogin(), code));
        log.info("Successfully generated and saved OTP code for user: {}", user.getId());

        return otpCodeMapper.toOtpCodeResponse(otpCode);
    }

    @Override
    public Boolean verifyCode(String code) {
        log.info("Starting code verification process");
        User user = getAuthUser();
        log.debug("Verifying code for user: {}", user.getId());
        OtpCode otpCode = otpCodeRepository.findFirstByUserAndCode(user, code)
                .orElseThrow(() -> {
                    log.error("Code not found for user: {}", user.getId());
                    return new NoSuchElementException("The code was not found for this user.");
                });
        if (otpCode.getStatus().equals(CodeStatus.ACTIVE)) {
            otpCode.setStatus(CodeStatus.USED);
            otpCodeRepository.save(otpCode);
            log.info("Code successfully verified and marked as used for user: {}", user.getId());
            return true;
        } else {
            log.warn("Attempt to verify non-active code for user: {}", user.getId());
            return false;
        }
    }

    private String processCode(int length) {
        StringBuilder processedCode = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            processedCode.append(CHAR_ARRAY[secureRandom.nextInt(CHAR_ARRAY.length)]);
        }
        return processedCode.toString();
    }

    private User getAuthUser() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return authUser.getUser();
    }
}
