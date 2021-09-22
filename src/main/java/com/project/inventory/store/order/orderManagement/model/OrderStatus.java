package com.project.inventory.store.order.orderManagement.model;

import java.io.Serializable;

public enum OrderStatus implements Serializable {
    PENDING ("PENDING"),
    CONFIRMED ("CONFIRMED"),
    COMPLETED ("COMPLETED"),
    DELIVERED ("DELIVERED"),
    CANCELLED ("CANCELLED"),
    CURRIER ("CURRIER"),
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
