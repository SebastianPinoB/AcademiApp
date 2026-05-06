package com.example.AcademiApp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleSqlError(DataIntegrityViolationException e) {
        // Esto captura errores de rut duplicado, email duplicado, etc.
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body("Error de integridad: El RUT o Email ya existe en el sistema.");
    }
}
