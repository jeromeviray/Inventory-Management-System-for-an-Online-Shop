package com.project.inventory.store.inventory.stock.model;

public class StockRequest {
    private int stock;
    private int threshold;

    public int getStock() {
        return stock;
    }

    public void setStock( int stock ) {
        this.stock = stock;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold( int threshold ) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
