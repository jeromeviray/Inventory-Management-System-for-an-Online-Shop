package com.project.inventory.exception;

public class InvalidException extends RuntimeException{
    public InvalidException() {
        super();
    }

    public InvalidException(String message) {
        super(message);
    }
}
