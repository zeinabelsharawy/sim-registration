package com.mobiletechnologies.bulkregistration.exceptions;

import java.io.IOException;

public class FileNotFoundException extends RuntimeException {

    private String message = "The file is empty!";

    public FileNotFoundException() {
    }

    public FileNotFoundException(String message) {
        super(message);
        this.message = message;
    }

}
