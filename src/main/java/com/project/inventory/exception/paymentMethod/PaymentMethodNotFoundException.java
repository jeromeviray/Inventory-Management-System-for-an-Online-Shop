package com.project.inventory.exception.paymentMethod;

public class PaymentMethodNotFoundException extends RuntimeException{
    public PaymentMethodNotFoundException() {
        super();
    }

    public PaymentMethodNotFoundException(String message) {
        super(message);
    }
}
