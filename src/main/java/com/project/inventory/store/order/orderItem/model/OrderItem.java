package com.project.inventory.store.order.orderItem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.inventory.store.order.orderManagement.model.Order;
import com.project.inventory.store.product.model.Product;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "order_items")
@Transactional
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "quantity", columnDefinition = "INT default 1", nullable = false)
    private int quantity;

    @Column(name = "amount", nullable = false)
    private double amount;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnoreProperties({"inventory", "incomingSupplyItems", "brand", "wishlist", "category", "description", "hibernateLazyInitializer"})
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="order_id", nullable = false)
    @JsonIgnore
    private Order order;

    public OrderItem() {
    }

    public OrderItem(int quantity, double amount, Product product, Order order) {
        this.quantity = quantity;
        this.amount = amount;
        this.product = product;
        this.order = order;
    }

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

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderItem)) return false;
        OrderItem orderItem = (OrderItem) o;
        return getId() == orderItem.getId() && getQuantity() == orderItem.getQuantity() && Double.compare(orderItem.getAmount(), getAmount()) == 0 && Objects.equals(getProduct(), orderItem.getProduct()) && Objects.equals(order, orderItem.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getAmount(), getProduct(), order);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", product=" + product +
                ", shoppingOrder=" + order +
                '}';
    }
}
