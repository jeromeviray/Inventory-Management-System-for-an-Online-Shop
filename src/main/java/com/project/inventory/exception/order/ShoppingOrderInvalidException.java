package com.project.inventory.exception.order;

public class ShoppingOrderInvalidException extends RuntimeException{
    public ShoppingOrderInvalidException() {
        super();
    }

    public ShoppingOrderInvalidException(String message) {
        super(message);
    }
}
