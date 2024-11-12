package com.restaurants.trazabilidad.aplication.exception;

import java.util.HashMap;
import java.util.Map;

import com.restaurants.trazabilidad.domain.exception.ErrorException;
import com.restaurants.trazabilidad.domain.exception.ErrorFeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ControlAdvice {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        ExceptionResponse exceptionResponse = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Error validation",
                errors
        );

        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ErrorFeignException.class)
    public ResponseEntity<?> handleErrorFeignException(ErrorFeignException ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getMessage());

        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.SERVICE_UNAVAILABLE.value(),
                "Service Unavailable",
                details
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(ErrorException.class)
    public ResponseEntity<?> handleErrorFeignException(ErrorException ex, WebRequest request) {
        Map<String, String> details = new HashMap<>();
        details.put("error", ex.getMessage());

        ExceptionResponse errorResponse = new ExceptionResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Service Unavailable",
                details
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}