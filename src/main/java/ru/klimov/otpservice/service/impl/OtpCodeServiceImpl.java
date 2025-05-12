package ru.klimov.otpservice.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.klimov.otpservice.dto.response.OtpCodeResponse;
import ru.klimov.otpservice.entity.OtpCode;
import ru.klimov.otpservice.entity.User;
import ru.klimov.otpservice.entity.enums.CodeStatus;
import ru.klimov.otpservice.mapper.OtpCodeMapper;
import ru.klimov.otpservice.repository.OtpCodeRepository;
import ru.klimov.otpservice.secutity.AuthUser;
import ru.klimov.otpservice.service.OtpCodeService;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OtpCodeServiceImpl implements OtpCodeService {

    private final OtpConfigurationServiceImpl otpConfigurationService;
    private final OtpCodeRepository otpCodeRepository;
    private final OtpCodeMapper otpCodeMapper;
    private final SecureRandom secureRandom = new SecureRandom();
    private final char[] CHAR_ARRAY = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();

    @Override
    public OtpCodeResponse generateOtpCode() {
        int length = otpConfigurationService.getConfiguration().getOtpLength();
        int expirationMinutes = otpConfigurationService.getConfiguration().getOtpExpirationMinutes();

        String code = processCode(length);

        User user = getAuthUser();
        OtpCode otpCode = new OtpCode();
        otpCode.setCode(code);
        otpCode.setExpiryDate(LocalDateTime.now().plusMinutes(expirationMinutes));
        otpCode.setStatus(CodeStatus.ACTIVE);
        otpCode.setUser(user);

        otpCodeRepository.save(otpCode);

        return otpCodeMapper.toOtpCodeResponse(otpCode);
    }

    @Override
    public Boolean verifyCode(String code) {
        User user = getAuthUser();
        OtpCode otpCode = otpCodeRepository.findFirstByUserAndCode(user, code)
                .orElseThrow(() -> new NoSuchElementException("The code was not found for this user."));
        if (otpCode.getStatus().equals(CodeStatus.ACTIVE)) {
            otpCode.setStatus(CodeStatus.USED);
            otpCodeRepository.save(otpCode);
            return true;
        } else {
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
