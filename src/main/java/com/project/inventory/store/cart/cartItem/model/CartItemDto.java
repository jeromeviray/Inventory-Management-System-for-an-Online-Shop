package com.project.inventory.store.cart.cartItem.model;

import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.model.ProductDto;

public class CartItemDto {

    private int id;
    private int quantity;
    private double amount;
//    @JsonFormat(pattern = "dd/MM/yyyy hh:mm:ss a")
    private String addedAt;
    private ProductAndInventoryDto product;


    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt( String addedAt ) {
        this.addedAt = addedAt;
    }


    public ProductAndInventoryDto getProduct() {
        return product;
    }

    public void setProduct( ProductAndInventoryDto product ) {
        this.product = product;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
