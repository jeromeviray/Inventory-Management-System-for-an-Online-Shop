package com.project.inventory.exception.invalid.cart;

public class CartItemNotValidException extends RuntimeException{
    public CartItemNotValidException(String message) {
        super(message);
    }
}
