package com.project.inventory.store.product.model;

import com.project.inventory.store.inventory.model.InventoryDto;

public class ProductAndInventoryDto {
    private ProductDto product;
    private InventoryDto inventory;

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct( ProductDto product ) {
        this.product = product;
    }

    public InventoryDto getInventory() {
        return inventory;
    }

    public void setInventory( InventoryDto inventory ) {
        this.inventory = inventory;
    }

}