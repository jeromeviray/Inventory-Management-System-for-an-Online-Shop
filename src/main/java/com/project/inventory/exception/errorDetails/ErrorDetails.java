package com.project.inventory.exception.errorDetails;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

import java.util.Date;

public class ErrorDetails {
    private HttpStatus status;
    @JsonFormat ( pattern = "hh:mm:ss dd-MM-yyyy", shape = JsonFormat.Shape.STRING )
    private Date timestamp;
    private String message;
    private String details;

    public ErrorDetails () {
    }

    public ErrorDetails ( HttpStatus status, Date timestamp, String message, String details ) {
        this.status = status;
        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public HttpStatus getStatus () {
        return status;
    }

    public void setStatus ( HttpStatus status ) {
        this.status = status;
    }

    public Date getTimestamp () {
        return timestamp;
    }

    public void setTimestamp ( Date timestamp ) {
        this.timestamp = timestamp;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }

    public String getDetails () {
        return details;
    }

    public void setDetails ( String details ) {
        this.details = details;
    }
}
