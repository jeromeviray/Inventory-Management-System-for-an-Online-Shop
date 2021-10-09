package com.project.inventory.store.product.model;

import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.product.wishlist.model.Wishlist;

public class ProductAndInventoryDto {
    private ProductDto product;
    private InventoryDto inventory;
    private Wishlist wishlist;

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

    public Wishlist getWishlist() {
        return wishlist;
    }

    public void setWishlist( Wishlist wishlist ) {
        this.wishlist = wishlist;
    }
}
