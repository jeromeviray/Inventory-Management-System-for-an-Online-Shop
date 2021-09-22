package com.project.inventory.store.incomingSupply.model;

public enum IncomingSupplyStatus {
    PENDING ("PENDING"),
    DELIVERED ("DELIVERED"),
    CANCELLED ("CANCELLED"),
    CURRIER ("CURRIER");

    String status;

    IncomingSupplyStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
