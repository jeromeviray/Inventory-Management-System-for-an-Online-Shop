package com.project.inventory.exception.customer;

public class CustomerAddressNotFoundException extends RuntimeException{
    public CustomerAddressNotFoundException() {
        super();
    }

    public CustomerAddressNotFoundException(String message) {
        super(message);
    }
}
