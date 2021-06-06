package com.project.inventory.exception.account;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException() {
        super();
    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}
