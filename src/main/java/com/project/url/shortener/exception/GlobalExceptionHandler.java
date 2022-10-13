package com.project.url.shortener.exception;

import com.project.url.shortener.rest.response.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleInvalidUrlException(InvalidUrlException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE.value()
                , exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleInvalidSchemaException(InvalidSchemaException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE.value()
                , exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleProxyNotFoundException(ProxyNotFoundException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_FOUND.value()
                , exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleJwtException(JWTException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.UNAUTHORIZED.value()
                , exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_FOUND.value()
                , exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.ALREADY_REPORTED.value()
                , exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.ALREADY_REPORTED);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleAliasAlreadyExistsException(AliasAlreadyTakenException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.NOT_ACCEPTABLE.value()
                , exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleInvalidInputException(InvalidInputException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.BAD_REQUEST.value(),
                 exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleBadCredentialsException(BadCredentialsException exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.UNAUTHORIZED.value(),
                "Invalid credentials", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponse> handleException(Exception exception){
        ExceptionResponse response = new ExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()
                , exception.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
