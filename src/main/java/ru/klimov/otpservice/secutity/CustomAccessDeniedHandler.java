package ru.klimov.otpservice.secutity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import ru.klimov.otpservice.dto.response.ResponseDto;
import ru.klimov.otpservice.entity.User;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        UUID currentUserId = getAuthUser().getId();
        String requestedUrl = request.getRequestURI();

        try {
            MDC.put("user_id", currentUserId.toString());
            MDC.put("requested_url", requestedUrl);
            log.warn("Access denied");
        } finally {
            MDC.clear();
        }

        ResponseDto responseDto = ResponseDto.builder()
                .message(accessDeniedException.getMessage())
                .result(false)
                .build();

        objectMapper.writeValue(response.getOutputStream(), responseDto);
    }

    private User getAuthUser() {
        AuthUser authUser = (AuthUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return authUser.getUser();
    }
}