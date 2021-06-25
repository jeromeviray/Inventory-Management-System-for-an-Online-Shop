package com.project.inventory.customer.payment.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.store.order.shoppingOrder.model.ShoppingOrder;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.Objects;

@Entity
@Table(name = "payment_method")
@Transactional
public class PaymentMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private int id;

    @Column(name = "payment_method")
    private String paymentMethod;

    @OneToOne(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
    @JsonIgnore
    private ShoppingOrder shoppingOrder;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentMethod)) return false;
        PaymentMethod that = (PaymentMethod) o;
        return getId() == that.getId() && Objects.equals(getPaymentMethod(), that.getPaymentMethod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPaymentMethod());
    }

    @Override
    public String toString() {
        return "PaymentMethod{" +
                "id=" + id +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
