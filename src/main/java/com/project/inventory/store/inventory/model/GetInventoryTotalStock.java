package com.project.inventory.store.inventory.model;

import com.project.inventory.store.inventory.stock.model.StockStatus;

public class GetInventoryTotalStock {
    private int id;
    private int threshold;
    private int totalStock;
    private String productName;
    private String status;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName( String productName ) {
        this.productName = productName;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold( int threshold ) {
        this.threshold = threshold;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock( int totalStock ) {
        this.totalStock = totalStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
