package com.project.inventory.common;

public class HttpResponse {
    private Boolean isSuccess;
    private String message;

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess( Boolean success ) {
        isSuccess = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage( String message ) {
        this.message = message;
    }
}
