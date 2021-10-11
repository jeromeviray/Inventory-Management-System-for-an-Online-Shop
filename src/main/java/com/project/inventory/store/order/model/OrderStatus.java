package com.project.inventory.store.order.model;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    PENDING ("PENDING"),
    CONFIRMED ("CONFIRMED"),
    PAYMENT_RECEIVED ("PAYMENT_RECEIVED"),
    DELIVERED ("DELIVERED"),
    CANCELLED ("CANCELLED"),
    SHIPPED ("SHIPPED"),
    NONE ("NONE");

    String status;

    OrderStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
