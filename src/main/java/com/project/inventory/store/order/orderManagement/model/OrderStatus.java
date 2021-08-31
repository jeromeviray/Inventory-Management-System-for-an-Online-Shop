package com.project.inventory.store.order.orderManagement.model;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    PENDING ("PENDING"),
    CONFIRMED ("PENDING"),
    COMPLETED ("PENDING"),
    DELIVERED ("PENDING"),
    CANCELLED ("PENDING"),
    CURRIER ("PENDING"),
    NONE ("PENDING");

    String status;

    OrderStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
