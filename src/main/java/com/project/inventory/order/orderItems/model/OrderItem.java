package com.project.inventory.order.orderItems.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.project.inventory.jsonView.View;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.product.model.Product;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "order_item")
@JsonView(value = View.PlaceOrder.class)
@Transactional
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int id;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "amount")
    private double amount;

    @CreationTimestamp
    @Column(name = "purchased_at")
    private Date purchasedAt;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinColumn(name = "order_id")
    private ShoppingOrder shoppingOrder;

    public OrderItem() {
    }

    public OrderItem(int quantity, double amount, Product product, ShoppingOrder shoppingOrder) {
        this.quantity = quantity;
        this.amount = amount;
        this.product = product;
        this.shoppingOrder = shoppingOrder;
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

    public Date getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt(Date purchasedAt) {
        this.purchasedAt = purchasedAt;
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
        return getId() == orderItem.getId() && getQuantity() == orderItem.getQuantity() && Double.compare(orderItem.getAmount(), getAmount()) == 0 && Objects.equals(getPurchasedAt(), orderItem.getPurchasedAt()) && Objects.equals(getProduct(), orderItem.getProduct()) && Objects.equals(shoppingOrder, orderItem.shoppingOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getQuantity(), getAmount(), getPurchasedAt(), getProduct(), shoppingOrder);
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", amount=" + amount +
                ", purchasedAt=" + purchasedAt +
                ", product=" + product +
                ", shoppingOrder=" + shoppingOrder +
                '}';
    }
}
