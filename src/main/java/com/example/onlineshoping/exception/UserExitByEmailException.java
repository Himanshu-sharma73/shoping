package com.example.onlineshoping.exception;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserExitByEmailException extends RuntimeException {
    private String errorCode;
    private String resourceName;
    private String fieldName;
    private String fieldValue;

    public UserExitByEmailException(String resourceName, String fieldName, String fieldValue, String errorCode) {
        super(String.format("%s not found with %s : %s",resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.errorCode = errorCode;
    }
}
