package com.project.inventory.exception.account;


public class AccountException extends RuntimeException{
    public AccountException() {
        super();
    }

    public AccountException(String message) {
        super(message);
    }
}
