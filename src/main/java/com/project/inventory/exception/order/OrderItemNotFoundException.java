package com.project.inventory.exception.order;

public class OrderItemNotFoundException extends RuntimeException{
    public OrderItemNotFoundException() {
        super();
    }

    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
