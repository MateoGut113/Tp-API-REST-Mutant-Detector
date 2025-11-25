package com.example.Mutantes.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.Mutantes.dto.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> detalles = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        ErrorResponse errorDTO = ErrorResponse.of(HttpStatus.BAD_REQUEST.value(),
                "Bad Request", detalles, request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.simple(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Error interno del servidor", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }

    @ExceptionHandler(HashNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleHashNotFound(HashNotFoundException ex, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.simple(HttpStatus.NOT_FOUND.value(),
                "HASH no encontrado", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        ErrorResponse errorDTO = ErrorResponse.simple(HttpStatus.BAD_REQUEST.value(),
                "Formato de parámetro inválido", ex.getMessage(), request.getRequestURI());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }
}
