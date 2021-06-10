package com.project.inventory.order.orders.model;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.customer.payment.model.PaymentMethod;
import com.project.inventory.jsonView.View;
import com.project.inventory.order.orderItems.model.OrderItem;
import com.project.inventory.permission.model.Account;
import jdk.jfr.Enabled;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "order")
@Transactional
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int id;

    @Column(name = "total_amount")
    private double totalAmount;

    @Column(name = "order_status")
    private String orderStatus;

    @Column(name = "delivered_at")
    private Date deliveredAt;

    @Column(name = "ordered_at")
    private Date orderedAt;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    @JsonView(value = {View.Account.class})
    private Account account;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_address_id")
    private CustomerAddress customerAddress;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(Date deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public Date getOrderedAt() {
        return orderedAt;
    }

    public void setOrderedAt(Date orderedAt) {
        this.orderedAt = orderedAt;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return getId() == order.getId() && Double.compare(order.getTotalAmount(), getTotalAmount()) == 0 && Objects.equals(getOrderStatus(), order.getOrderStatus()) && Objects.equals(getDeliveredAt(), order.getDeliveredAt()) && Objects.equals(getOrderedAt(), order.getOrderedAt()) && Objects.equals(getAccount(), order.getAccount()) && Objects.equals(getCustomerAddress(), order.getCustomerAddress()) && Objects.equals(getPaymentMethod(), order.getPaymentMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTotalAmount(), getOrderStatus(), getDeliveredAt(), getOrderedAt(), getAccount(), getCustomerAddress(), getPaymentMethod());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", orderStatus='" + orderStatus + '\'' +
                ", deliveredAt=" + deliveredAt +
                ", orderedAt=" + orderedAt +
                ", account=" + account +
                ", customerAddress=" + customerAddress +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}
