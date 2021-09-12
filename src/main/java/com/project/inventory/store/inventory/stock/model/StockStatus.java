package com.project.inventory.store.inventory.stock.model;

import java.io.Serializable;

public enum StockStatus implements Serializable {
    OK ("OK"),
    OUT_OF_STOCK ("OUT OF STOCK"),
    MID ("MID"),
    LOW ("LOW");

    String status;

    StockStatus(String status){
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
