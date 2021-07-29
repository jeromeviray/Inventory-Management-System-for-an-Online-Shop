package com.project.inventory.exception.serverError.product;

public class ProductNotUpdatedException extends RuntimeException{
    public ProductNotUpdatedException(String message) {
        super(message);
    }
}
