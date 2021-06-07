package com.project.inventory.exception.cartItem;

public class CartItemNotValidException extends RuntimeException{
    public CartItemNotValidException(String message) {
        super(message);
    }
}
