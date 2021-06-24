package com.project.inventory.exception.account;

public class PasswordException extends RuntimeException{
    public PasswordException() {
        super();
    }

    public PasswordException(String message) {
        super(message);
    }
}
