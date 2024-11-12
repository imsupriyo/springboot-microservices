package com.example.employeeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private final String resource;
    private final String fieldName;
    private final Long fieldValue;

    public ResourceNotFoundException(String resource, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : %d", resource, fieldName, fieldValue));
        this.resource = resource;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
