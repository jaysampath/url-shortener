package com.project.url.shortener.exception;

public class InvalidSchemaException extends RuntimeException{

    public InvalidSchemaException(String message){
        super(message);
    }
}
