package com.hritvik.user_access_management.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.hritvik.user_access_management.constant.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


@ControllerAdvice
@Log4j2
public class CustomExceptionHandler {

    // Handle Access Denied Exception
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to perform this action");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password: " + ex.getMessage());
    }

    // Handle Entity Not Found Exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Resource not found: " + ex.getMessage());
    }

    // Handle all other exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGlobalException(Exception ex, WebRequest request) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + ex.getMessage());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationException(ValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        FieldError fieldError = ex.getBindingResult().getFieldError();
        if (fieldError != null) {
            return new ResponseEntity<>(fieldError.getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JsonMappingException.class)
    @ResponseBody
    public ResponseEntity<String> handleJsonMappingException(JsonMappingException ex) {
        if (ex.getCause() instanceof InvalidFormatException ife) {
            if (ife.getTargetType().equals(Role.class)) {
                return new ResponseEntity<>("Invalid role. Allowed values are USER or ADMIN.", HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public ResponseEntity<String> handleInvalidFormatException(InvalidFormatException ex) {
        // Check if the exception is due to an enum value
        if (ex.getTargetType().equals(Role.class)) {
            return new ResponseEntity<>("Invalid role. Allowed values are USER or ADMIN.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Invalid input", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        String message = ex.getMessage();

        // Check if the exception message contains the unique constraint violation for email
        if (message.contains("PUBLIC.CONSTRAINT_INDEX_4 ON PUBLIC.USERS") || message.contains("USERS(EMAIL)")) {
            return new ResponseEntity<>("Email already exists. Please choose a different email address.", HttpStatus.BAD_REQUEST);
        }

        // Check if the exception message contains the unique constraint violation for username
        if (message.contains("PUBLIC.CONSTRAINT_INDEX_4D ON PUBLIC.USERS") || message.contains("USERS(USERNAME)")) {
            return new ResponseEntity<>("Username already exists. Please choose a different username.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Database error occurred.", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

