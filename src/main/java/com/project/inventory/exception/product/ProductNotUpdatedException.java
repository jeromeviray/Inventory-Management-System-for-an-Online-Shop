package com.project.inventory.exception.product;

public class ProductNotUpdatedException extends RuntimeException{
    public ProductNotUpdatedException(String message) {
        super(message);
    }
}
