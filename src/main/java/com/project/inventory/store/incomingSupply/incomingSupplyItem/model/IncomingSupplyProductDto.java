package com.project.inventory.store.incomingSupply.incomingSupplyItem.model;

public class IncomingSupplyProductDto {
    private int id;
    private String name;
    private int barcode;
    private double price;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode( int barcode ) {
        this.barcode = barcode;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice( double price ) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
