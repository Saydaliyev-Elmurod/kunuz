package com.example.kunuz.exps;

public class AppBadRequestException extends RuntimeException{
    public AppBadRequestException() {
        super();
    }

    public AppBadRequestException(String message) {
        super(message);
    }
}
