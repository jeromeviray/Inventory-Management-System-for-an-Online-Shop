package com.project.inventory.store.product.brand.model;

public class BrandDto {
    private int id;
    private String brand;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand( String brand ) {
        this.brand = brand;
    }

    @Override
    public String toString() {
        return super.toString();
    }

}
