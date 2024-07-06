package com.ellyanf.picpayrest.exception;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(ForbiddenOperationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorDetails handleForbiddenOperationException(@NotNull ForbiddenOperationException ex, @NotNull WebRequest request) {
        return new ErrorDetails(ex.getMessage(), request.getDescription(true));
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(@NotNull JdbcSQLIntegrityConstraintViolationException ex) {
        return ResponseEntity.badRequest().body("An user with this email/document already exists.");
    }

    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDetails handleNoSuchElementException(@NotNull NoSuchElementException ex, @NotNull WebRequest request) {
        return new ErrorDetails(ex.getLocalizedMessage(), request.getDescription(true));
    }
}
