package com.project.inventory.store.product.model;

import com.project.inventory.store.information.model.GetStoreBranchDto;

import java.util.List;

public class ProductDto {

    private int id;
    private String productName;
    private String productDescription;
    private double productPrice;
    private List<FileImageDto> fileImages;
    private GetStoreBranchDto storeInformation;

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

    public List<FileImageDto> getFileImages() {
        return fileImages;
    }

    public void setFileImages( List<FileImageDto> fileImages ) {
        this.fileImages = fileImages;
    }


    public GetStoreBranchDto getStoreInformation() {
        return storeInformation;
    }

    public void setStoreInformation( GetStoreBranchDto storeInformation ) {
        this.storeInformation = storeInformation;
    }

    @Override
    public String toString() {
        return "ProductDto{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPrice=" + productPrice +
                ", fileImages=" + fileImages +
                ", storeInformation=" + storeInformation +
                '}';
    }
}
