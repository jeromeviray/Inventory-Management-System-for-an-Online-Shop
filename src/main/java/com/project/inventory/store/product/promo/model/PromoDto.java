package com.project.inventory.store.product.promo.model;

public class PromoDto {
    private int id;
    private int quantity;
    private int soldQuantity;
    private int percentage;
    private String startDate;
    private String endDate;
    private String status;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PromoDto{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", soldQuantity=" + soldQuantity +
                ", percentage=" + percentage +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
