package ru.klimov.otpservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.klimov.otpservice.dto.response.OtpCodeResponse;
import ru.klimov.otpservice.entity.OtpCode;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OtpCodeMapper {

    OtpCodeResponse toOtpCodeResponse(OtpCode otpCode);
}
