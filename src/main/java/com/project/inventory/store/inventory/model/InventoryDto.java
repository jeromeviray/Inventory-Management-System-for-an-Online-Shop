package com.project.inventory.store.inventory.model;

public class InventoryDto {
    private int threshold;
    private int totalStock;
    private String status;

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
