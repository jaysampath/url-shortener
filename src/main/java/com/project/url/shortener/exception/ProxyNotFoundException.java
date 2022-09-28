package com.project.url.shortener.exception;

public class ProxyNotFoundException extends RuntimeException{

    public ProxyNotFoundException(String message){
        super(message);
    }
}
