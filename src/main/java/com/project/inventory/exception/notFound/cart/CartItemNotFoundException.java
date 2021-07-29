package com.project.inventory.exception.notFound.cart;

public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException() {
        super();
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }
}
