package com.project.inventory.exception.cartItem;

public class CartItemNotFoundException extends RuntimeException{
    public CartItemNotFoundException() {
        super();
    }

    public CartItemNotFoundException(String message) {
        super(message);
    }
}
