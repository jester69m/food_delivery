package com.auth.util;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends RuntimeException {

    private HttpStatus statusCode;

    public ResourceNotFoundException(HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}
