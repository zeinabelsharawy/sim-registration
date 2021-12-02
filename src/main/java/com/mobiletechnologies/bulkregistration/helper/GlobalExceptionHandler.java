package com.mobiletechnologies.bulkregistration.helper;

import com.mobiletechnologies.bulkregistration.exceptions.EmptyFileException;
import com.mobiletechnologies.bulkregistration.exceptions.FileNotFoundException;
import com.mobiletechnologies.bulkregistration.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.UnexpectedTypeException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({EmptyFileException.class})
    public ErrorResponse handleEmptyFileException(EmptyFileException e) {
        return ErrorResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler({FileNotFoundException.class})
    public ErrorResponse handleFileNotFoundException(FileNotFoundException e) {
        System.out.println("------------------------------------");
        return ErrorResponse.badRequest(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class,
            UnexpectedTypeException.class})
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
