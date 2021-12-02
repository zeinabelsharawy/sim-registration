package com.mobiletechnologies.bulkregistration.response;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Getter
@ToString
public class ErrorResponse {
    private final int status;
    private final String reason;
    private final String detail;


    public ErrorResponse(HttpStatus status, String detail) {
        this.detail = detail;
        this.status = status.value();
        this.reason = status.getReasonPhrase();
    }

    public static ErrorResponse badRequest(String message) {
        return status(HttpStatus.BAD_REQUEST, message);
    }

    public static ErrorResponse forbidden(String message) {
        return status(HttpStatus.FORBIDDEN, message);
    }

    public static ErrorResponse notFound(String message) {
        return status(HttpStatus.NOT_FOUND, message);
    }

    public static ErrorResponse unAuthorized(String message) {
        return status(HttpStatus.UNAUTHORIZED, message);
    }

    public static ErrorResponse internalServerError(String message) {
        return status(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public static ErrorResponse status(HttpStatus status, String message) {
        return new ErrorResponse(status, message);
    }
}
