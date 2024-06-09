package com.example.community.exception;

import lombok.Data;


public class myException extends RuntimeException {
    private String message;

    public myException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

