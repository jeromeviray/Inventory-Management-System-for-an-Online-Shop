package com.project.inventory.store.order.orderItem.model;

import com.project.inventory.store.product.comment.model.Comment;
import com.project.inventory.store.product.model.ProductDto;

import java.util.Date;

public class OrderItemDto {
    private int id;
    private int quantity;
    private double amount;
    private Date purchasedAt;
    private ProductDto product;
    private Comment comment;

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

    public ProductDto getProduct() {
        return product;
    }

    public void setProduct(ProductDto product) {
        this.product = product;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment( Comment comment ) {
        this.comment = comment;
    }
}
