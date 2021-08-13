package com.project.inventory.exception.notFound.customer;

public class CustomerAddressNotFoundException extends RuntimeException{
    public CustomerAddressNotFoundException() {
        super();
    }

    public CustomerAddressNotFoundException(String message) {
        super(message);
    }
}
