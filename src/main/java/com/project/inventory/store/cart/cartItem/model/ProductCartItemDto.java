package com.project.inventory.store.cart.cartItem.model;

import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.product.brand.model.BrandDto;
import com.project.inventory.store.product.category.model.CategoryDto;
import com.project.inventory.store.product.model.FileImageDto;
import com.project.inventory.store.product.promo.model.PromoDto;

import java.util.List;

public class ProductCartItemDto {
    private int id;
    private String productName;
    private String productDescription;
    private double productPrice;
    private int barcode;
    private BrandDto brand;
    private CategoryDto category;
    private List<FileImageDto> fileImages;
    private PromoDto promo;
    private InventoryDto inventory;

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

    public PromoDto getPromo() {
        return promo;
    }

    public void setPromo( PromoDto promo ) {
        this.promo = promo;
    }

    public InventoryDto getInventory() {
        return inventory;
    }

    public void setInventory( InventoryDto inventory ) {
        this.inventory = inventory;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
