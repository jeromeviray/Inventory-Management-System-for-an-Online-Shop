package com.project.inventory.store.product.promo.model;

public class PromoRequest {
    private int quantity;
    private int soldQuantity;
    private int percentage;
    private String startDate;
    private String endDate;
    private int productId;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity( int quantity ) {
        this.quantity = quantity;
    }

    public int getSoldQuantity() {
        return soldQuantity;
    }

    public void setSoldQuantity( int soldQuantity ) {
        this.soldQuantity = soldQuantity;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage( int percentage ) {
        this.percentage = percentage;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate( String startDate ) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate( String endDate ) {
        this.endDate = endDate;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId( int productId ) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
