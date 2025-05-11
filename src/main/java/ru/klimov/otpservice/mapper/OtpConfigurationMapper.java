package ru.klimov.otpservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import ru.klimov.otpservice.dto.response.OtpConfigurationResponseDto;
import ru.klimov.otpservice.entity.OtpConfiguration;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OtpConfigurationMapper {

    OtpConfigurationResponseDto toOtpConfigurationResponseDto(OtpConfiguration otpConfiguration);
}
