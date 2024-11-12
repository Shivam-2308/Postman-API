package com.example.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private String message;
    private HttpStatus status;

    public ApiException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}

