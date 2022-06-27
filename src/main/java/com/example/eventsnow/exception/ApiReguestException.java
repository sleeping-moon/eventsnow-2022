package com.example.eventsnow.exception;

public class ApiReguestException extends RuntimeException{

    public ApiReguestException(String message) {
        super(message);
    }

    public ApiReguestException(String message, Throwable cause) {
        super(message, cause);
    }
}
