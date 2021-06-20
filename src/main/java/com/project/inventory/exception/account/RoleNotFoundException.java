package com.project.inventory.exception.account;

public class RoleNotFoundException extends RuntimeException{
    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(String message) {
        super(message);
    }
}
