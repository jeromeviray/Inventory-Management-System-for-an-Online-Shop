package com.project.inventory.store.product.promo.model;

public enum PromoStatus {
    ONGOING ("ONGOING"),
    END ("END"),
    UNSCHEDULED ("UNSCHEDULED");

    String status;

    PromoStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
