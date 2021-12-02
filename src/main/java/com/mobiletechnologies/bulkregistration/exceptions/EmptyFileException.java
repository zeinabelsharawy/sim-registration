package com.mobiletechnologies.bulkregistration.exceptions;

public class EmptyFileException extends RuntimeException {

    private String message = "The file is empty!";

    public EmptyFileException() {
    }

    public EmptyFileException(String message) {
        super(message);
        this.message = message;
    }

}
