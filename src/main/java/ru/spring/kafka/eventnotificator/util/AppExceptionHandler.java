package ru.spring.kafka.eventnotificator.util;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorDto> handleGeneralException(ValidationException ex) {
        ErrorDto errorDto = new ErrorDto(
                "Внутренняя ошибка сервера",
                ex.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDto);
    }
}
