package com.project.url.shortener.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
