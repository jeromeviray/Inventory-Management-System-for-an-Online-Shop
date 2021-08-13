package com.project.inventory.exception.invalid;

public class InvalidException extends RuntimeException{
    public InvalidException() {
        super();
    }

    public InvalidException(String message) {
        super(message);
    }
}
