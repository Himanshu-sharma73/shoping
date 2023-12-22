package com.example.onlineshoping.exception;

import lombok.AllArgsConstructor;

public class UserRoleNotFoundException extends RuntimeException {
    private String errorCode;
    private String resourceName;
    private String message;

    public UserRoleNotFoundException(String resourceName, String message, String errorCode) {
        super(String.format("%s not found with %s : %s",resourceName, message));
        this.resourceName = resourceName;
        this.message = message;
        this.errorCode = errorCode;
    }
}
