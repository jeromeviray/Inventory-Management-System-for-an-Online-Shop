package com.project.inventory.exception.notFound.cart;

public class CartNotFound extends RuntimeException{
    public CartNotFound() {
        super();
    }

    public CartNotFound(String message) {
        super(message);
    }
}
