package com.project.inventory.store.inventory.model;

import com.project.inventory.store.product.model.FileImageDto;

import java.util.List;

public class GetProductDto {

    private int id;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int barcode;
    private List<FileImageDto> fileImages;

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

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription( String productDescription ) {
        this.productDescription = productDescription;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice( double productPrice ) {
        this.productPrice = productPrice;
    }

    public int getBarcode() {
        return barcode;
    }

    public void setBarcode( int barcode ) {
        this.barcode = barcode;
    }

    public List<FileImageDto> getFileImages() {
        return fileImages;
    }

    public void setFileImages( List<FileImageDto> fileImages ) {
        this.fileImages = fileImages;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}