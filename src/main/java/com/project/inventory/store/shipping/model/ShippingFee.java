package com.project.inventory.store.shipping.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.store.order.model.Order;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "shipping_fee")
public class ShippingFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "shipping_amount")
    private double shippingAmount;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a")
    @CreationTimestamp
    @Column( name = "created_at", updatable = false, nullable = false, columnDefinition = "DATETIME default current_timestamp" )
    private Date createdAt;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss a")
    @UpdateTimestamp
    @Column( name = "updated_at", nullable = false, columnDefinition = "DATETIME default current_timestamp on update current_timestamp" )
    private Date updatedAt;
//    @OneToMany(mappedBy = "shippingFee", fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<Order> orders;


    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public double getShippingAmount() {
        return shippingAmount;
    }

    public void setShippingAmount( double shippingAmount ) {
        this.shippingAmount = shippingAmount;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt( Date createdAt ) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt( Date updatedAt ) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        return super.equals( obj );
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
