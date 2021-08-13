package com.project.inventory.exception.apiError;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class ApiError {
    @JsonFormat ( pattern = "hh:mm:ss dd-MM-yyyy", shape = JsonFormat.Shape.STRING )
    private Date timestamp;
    private int code;
    private HttpStatus error;
    private String message;
    private String details;

    public ApiError() {
    }

    public ApiError(Date timestamp, int code, HttpStatus error, String message) {
        this.timestamp = timestamp;
        this.code = code;
        this.error = error;
        this.message = message;
    }

    public ApiError(Date timestamp, int code, HttpStatus error, String message, String details) {
        this.timestamp = timestamp;
        this.code = code;
        this.error = error;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ApiError{" +
                "timestamp=" + timestamp +
                ", code=" + code +
                ", error=" + error +
                ", message='" + message + '\'' +
                ", details='" + details + '\'' +
                '}';
    }
}
