package ru.klimov.otpservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.klimov.otpservice.entity.OtpCode;
import ru.klimov.otpservice.entity.User;
import ru.klimov.otpservice.entity.enums.CodeStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpCodeRepository extends JpaRepository<OtpCode, UUID> {

    Boolean existsOtpCodesByUserAndStatusAndCode(User user, CodeStatus status, String code);

    Optional<OtpCode> findFirstByUserAndCode(User user, String code);

    List<OtpCode> findAllByStatus(CodeStatus status);
}
