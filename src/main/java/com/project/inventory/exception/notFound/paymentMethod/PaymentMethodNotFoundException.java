package com.project.inventory.exception.notFound.paymentMethod;

public class PaymentMethodNotFoundException extends RuntimeException{
    public PaymentMethodNotFoundException() {
        super();
    }

    public PaymentMethodNotFoundException(String message) {
        super(message);
    }
}
