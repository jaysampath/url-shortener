package com.project.url.shortener.exception;

public class JWTException extends RuntimeException{

    public JWTException(String message){
        super(message);
    }
}
