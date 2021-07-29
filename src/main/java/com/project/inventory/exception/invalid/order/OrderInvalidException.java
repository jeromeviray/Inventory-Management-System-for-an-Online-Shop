package com.project.inventory.exception.invalid.order;

public class OrderInvalidException extends RuntimeException{
    public OrderInvalidException() {
        super();
    }

    public OrderInvalidException(String message) {
        super(message);
    }
}
