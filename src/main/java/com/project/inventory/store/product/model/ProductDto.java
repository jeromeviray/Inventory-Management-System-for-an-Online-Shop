package com.project.inventory.store.product.model;

import com.project.inventory.store.information.branch.model.GetBranchDto;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;

import java.util.List;

public class ProductDto {

    private int id;
    private String productName;
    private String productDescription;
    private double productPrice;
    private List<FileImageDto> fileImages;
    private GetBranchDto branch;
//    private GetStockDto inventory;
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

    public List<FileImageDto> getFileImages() {
        return fileImages;
    }

    public void setFileImages( List<FileImageDto> fileImages ) {
        this.fileImages = fileImages;
    }

    public GetBranchDto getBranch() {
        return branch;
    }

    public void setBranch( GetBranchDto branch ) {
        this.branch = branch;
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
