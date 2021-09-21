package com.project.inventory.store.inventory.model;

import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductDto;

public class InventoryDto {
    private int threshold;
    private int totalStock;
    private String status;
    private GetProductDto product;

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold( int threshold ) {
        this.threshold = threshold;
    }

    public int getTotalStock() {
        return totalStock;
    }

    public void setTotalStock( int totalStock ) {
        this.totalStock = totalStock;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus( String status ) {
        this.status = status;
    }

    public GetProductDto getProduct() {
        return product;
    }

    public void setProduct( GetProductDto product ) {
        this.product = product;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
