package com.project.inventory.store.order.model;

import java.io.Serializable;

public enum PaymentStatus implements Serializable {
    PENDING ("PENDING"),
    PAYMENT_RECEIVED ("PAYMENT_RECEIVED"),
    PAYMENT_FAILED ("PAYMENT_FAILED");

    String status;

    PaymentStatus( String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
