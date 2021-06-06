package com.project.inventory.order.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.product.model.Product;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.ManyToAny;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_amount")
    private double totalAmount;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated;

    @Column(name = "is_removed")
    private boolean isRemoved;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isRemoved() {
        return isRemoved;
    }

    public void setRemoved(boolean removed) {
        isRemoved = removed;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return getId() == orderItem.getId() && getQuantity() == orderItem.getQuantity() && Double.compare(orderItem.getTotalAmount(), getTotalAmount()) == 0 && isRemoved() == orderItem.isRemoved() && Objects.equals(getCreated(), orderItem.getCreated()) && Objects.equals(getUpdated(), orderItem.getUpdated()) && Objects.equals(getProduct(), orderItem.getProduct()) && Objects.equals(getCart(), orderItem.getCart());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getTotalAmount(), getCreated(), getUpdated(), isRemoved(), getProduct(), getCart());
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", totalAmount=" + totalAmount +
                ", created=" + created +
                ", updated=" + updated +
                ", isRemoved=" + isRemoved +
                ", product=" + product +
                ", cart=" + cart +
                '}';
    }
}
