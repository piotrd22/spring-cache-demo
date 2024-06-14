package com.example.demo.config;

import com.example.demo.exceptions.NotFoundException;
import com.example.demo.exceptions.SavingEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SavingEntityException.class)
    protected ResponseEntity<CustomError> handleCreatingEntityException(SavingEntityException savingEntityException, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", savingEntityException.getMessage());

        return new ResponseEntity<>(parseCustomError(HttpStatus.UNPROCESSABLE_ENTITY, errors, request), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<CustomError> handleNotFoundException(NotFoundException notFoundException, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error", notFoundException.getMessage());

        return new ResponseEntity<>(parseCustomError(HttpStatus.NOT_FOUND, errors, request), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        Objects.requireNonNull(ex.getBindingResult()).getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return new ResponseEntity<>(parseCustomError(HttpStatus.BAD_REQUEST, errors, request), HttpStatus.BAD_REQUEST);
    }

    private CustomError parseCustomError(HttpStatus httpStatus, Map<String, String> errors, WebRequest request) {
        return new CustomError(httpStatus.value(), httpStatus, errors, getPath(request), Instant.now());
    }

    private String getPath(WebRequest request) {
        return ((ServletWebRequest) request).getRequest().getRequestURI();
    }

    public record CustomError(int statusCode, HttpStatus status, Map<String, String> errors, String path, Instant timestamp) { }
}
