package com.project.inventory.exception.notFound.order;

public class OrderItemNotFoundException extends RuntimeException{
    public OrderItemNotFoundException() {
        super();
    }

    public OrderItemNotFoundException(String message) {
        super(message);
    }
}
