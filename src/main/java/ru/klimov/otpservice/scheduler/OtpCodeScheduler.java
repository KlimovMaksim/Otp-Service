package ru.klimov.otpservice.scheduler;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.klimov.otpservice.entity.enums.CodeStatus;
import ru.klimov.otpservice.repository.OtpCodeRepository;

import java.time.LocalDateTime;

@Slf4j
@Component
@AllArgsConstructor
public class OtpCodeScheduler {

    private final OtpCodeRepository otpCodeRepository;

    @Scheduled(cron = "0 */1 * * * *")
    public void updateOtpCodeStatus() {
        log.debug("Starting expired OTP codes check");
        otpCodeRepository.findAllByStatus(CodeStatus.ACTIVE)
                .forEach(otpCode -> {
                    if (otpCode.getExpiryDate().isBefore(LocalDateTime.now())) {
                        log.info("OTP code {} expired for user {}", otpCode.getCode(), otpCode.getUser().getLogin());
                        otpCode.setStatus(CodeStatus.EXPIRED);
                        otpCodeRepository.save(otpCode);
                    }
                });
        log.debug("Finished expired OTP codes check");
    }
}
