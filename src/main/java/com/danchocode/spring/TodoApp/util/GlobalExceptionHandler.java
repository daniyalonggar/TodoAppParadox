package com.danchocode.spring.TodoApp.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Обработка кастомного исключения PersonNotFoundException
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<PersonErrorResponse> handlePersonNotFound(PersonNotFoundException ex) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Person not found",
                Instant.now().toEpochMilli()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Обработка UsernameNotFoundException (из Spring Security)
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<PersonErrorResponse> handleUsernameNotFound(UsernameNotFoundException ex) {
        PersonErrorResponse response = new PersonErrorResponse(
                ex.getMessage(),
                Instant.now().toEpochMilli()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    // Обработка ошибок валидации @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    // Общий обработчик любых других исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<PersonErrorResponse> handleGeneralException(Exception ex) {
        PersonErrorResponse response = new PersonErrorResponse(
                "Internal server error: " + ex.getMessage(),
                Instant.now().toEpochMilli()
        );
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}