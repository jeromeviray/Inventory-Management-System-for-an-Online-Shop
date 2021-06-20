package com.project.inventory.order.orderItems.model;

import com.project.inventory.product.model.ProductToDto;

import java.util.Date;

public class OrderItemDto {
    private int id;
    private int quantity;
    private double amount;
    private Date purchasedAt;
    private ProductToDto product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Date purchasedAt) {
        this.purchasedAt = purchasedAt;
    }

    public ProductToDto getProduct() {
        return product;
    }

    public void setProduct(ProductToDto product) {
        this.product = product;
    }
}
