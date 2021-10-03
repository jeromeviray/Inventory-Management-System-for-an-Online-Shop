package com.project.inventory.store.inventory.stock.model;

public class StockDto {
    private int id;
    private String stockId;
    private int stock;
    private String added;
    private String updated;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId( String stockId ) {
        this.stockId = stockId;
    }

    public int getStock() {
        return stock;
    }

    public void setStock( int stock ) {
        this.stock = stock;
    }

    public String getAdded() {
        return added;
    }

    public void setAdded( String added ) {
        this.added = added;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated( String updated ) {
        this.updated = updated;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        return super.equals( obj );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
