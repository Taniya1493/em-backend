package com.employee.emsbackend.exception;

import org.springframework.http.HttpStatus;


import org.springframework.http.HttpStatus;

public class EmsAPIException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public EmsAPIException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public EmsAPIException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

