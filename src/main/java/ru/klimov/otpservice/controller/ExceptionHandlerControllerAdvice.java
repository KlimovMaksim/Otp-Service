package ru.klimov.otpservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.klimov.otpservice.dto.response.ResponseDto;
import ru.klimov.otpservice.exception.RegistrationException;
import ru.klimov.otpservice.exception.UserNotFoundException;

import java.util.NoSuchElementException;


@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({UserNotFoundException.class, NoSuchElementException.class})
    public ResponseEntity<ResponseDto> handleExceptionForNotFoundHttpStatus(Exception ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(getResponseDto(ex.getMessage()));
    }

    @ExceptionHandler({RegistrationException.class, IllegalArgumentException.class, UnsupportedOperationException.class,
            SecurityException.class, IllegalStateException.class})
    public ResponseEntity<ResponseDto> handleExceptionForBadRequestHttpStatus(Exception ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getResponseDto(ex.getMessage()));
    }

    private ResponseDto getResponseDto(String message) {
        return ResponseDto.builder()
                .message(message)
                .build();
    }
}
