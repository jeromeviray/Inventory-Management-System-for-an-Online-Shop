package com.project.inventory.order.shoppingOrder.model;

import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.order.orderItems.model.OrderItem;
import com.project.inventory.permission.model.Account;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "shopping_order")
public class ShoppingOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @Column(name = "order_status")
    private OrderStatus orderStatus;

    @Column(name = "total_amount")
    private double totalAmount;

    @CreationTimestamp
    @Column(name = "ordered_at")
    private Date orderedAt;

    @UpdateTimestamp
    @Column(name = "delivered_at")
    private Date deliveredAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private PaymentMethod paymentMethod;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_address_id")
    private CustomerAddress customerAddress;

    @OneToMany(mappedBy = "shoppingOrder", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ShoppingOrder)) return false;
        ShoppingOrder that = (ShoppingOrder) o;
        return getId() == that.getId() && Double.compare(that.getTotalAmount(), getTotalAmount()) == 0 && getOrderStatus() == that.getOrderStatus() && Objects.equals(getOrderedAt(), that.getOrderedAt()) && Objects.equals(getDeliveredAt(), that.getDeliveredAt()) && Objects.equals(getAccount(), that.getAccount()) && Objects.equals(getPaymentMethod(), that.getPaymentMethod()) && Objects.equals(getCustomerAddress(), that.getCustomerAddress()) && Objects.equals(getOrderItems(), that.getOrderItems());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getOrderStatus(), getTotalAmount(), getOrderedAt(), getDeliveredAt(), getAccount(), getPaymentMethod(), getCustomerAddress(), getOrderItems());
    }

    @Override
    public String toString() {
        return "ShoppingOrder{" +
                "id=" + id +
                ", orderStatus=" + orderStatus +
                ", totalAmount=" + totalAmount +
                ", orderedAt=" + orderedAt +
                ", deliveredAt=" + deliveredAt +
                ", account=" + account +
                ", paymentMethod=" + paymentMethod +
                ", customerAddress=" + customerAddress +
                ", orderItems=" + orderItems +
                '}';
    }
}
