package com.project.inventory.store.incomingSupply.incomingSupplyItem.model;

public class IncomingSupplyItemDto {
    private int id;
    private int numberReceived;
    private IncomingSupplyProductDto product;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getNumberReceived() {
        return numberReceived;
    }

    public void setNumberReceived( int numberReceived ) {
        this.numberReceived = numberReceived;
    }

    public IncomingSupplyProductDto getProduct() {
        return product;
    }

    public void setProduct( IncomingSupplyProductDto product ) {
        this.product = product;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
