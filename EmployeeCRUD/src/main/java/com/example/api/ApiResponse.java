package com.example.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse {
    private String message;
    private int status;

    public ApiResponse(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
