package com.project.inventory.exception.cart;

public class CartNotFound extends RuntimeException{
    public CartNotFound() {
        super();
    }

    public CartNotFound(String message) {
        super(message);
    }
}
