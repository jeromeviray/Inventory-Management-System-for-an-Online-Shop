package com.project.inventory.store.product.model;

import com.project.inventory.store.product.brand.model.BrandDto;
import com.project.inventory.store.product.category.model.CategoryDto;

import java.util.List;

public class ProductDto {

    private int id;
    private String productName;
    private String productDescription;
    private double productPrice;
    private String barcode;
    private BrandDto brand;
    private CategoryDto category;
    private List<FileImageDto> fileImages;
    private Integer rating;

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

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode( String barcode ) {
        this.barcode = barcode;
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand( BrandDto brand ) {
        this.brand = brand;
    }

    public CategoryDto getCategory() {
        return category;
    }

    public void setCategory( CategoryDto category ) {
        this.category = category;
    }

    public List<FileImageDto> getFileImages() {
        return fileImages;
    }

    public void setFileImages( List<FileImageDto> fileImages ) {
        this.fileImages = fileImages;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating( Integer rating ) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
