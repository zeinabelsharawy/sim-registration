package com.mobiletechnologies.bulkregistration.exceptions;

public class InvalidInputsException extends RuntimeException {
    private String message;

    public InvalidInputsException(String message) {
        super(message);
        this.message = message;
    }

    public InvalidInputsException() {
    }
}

