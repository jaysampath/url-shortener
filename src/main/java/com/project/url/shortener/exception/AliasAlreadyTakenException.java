package com.project.url.shortener.exception;

public class AliasAlreadyTakenException extends RuntimeException{

    public AliasAlreadyTakenException(String message){
        super(message);
    }
}
