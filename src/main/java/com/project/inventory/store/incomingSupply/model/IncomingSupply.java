package com.project.inventory.store.incomingSupply.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.store.incomingSupply.incomingSupplyItem.model.IncomingSupplyItem;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.supplier.model.Supplier;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "incoming_supplies")
public class IncomingSupply {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "supply_reference")
    private String supplyReference;
    @Column(name = "purchased_at")
    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date purchasedAt;

    @Column(name ="delivered_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date deliveredAt;

    @UpdateTimestamp
    @Column(name ="updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date updatedAt;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private IncomingSupplyStatus incomingSupplyStatus;

    @OneToMany(mappedBy = "incomingSupply", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<IncomingSupplyItem> incomingSupplyItems;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "supply_id")
    private Supplier supplier;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getSupplyReference() {
        return supplyReference;
    }
    @PostPersist
    public void setSupplyReference( ) {
        int startingPoint = 100000;
        int count = startingPoint + this.id;
        if(this.id != 0){
            this.supplyReference = "IS-REF-"+count;
        }
    }

    public Date getPurchasedAt() {
        return purchasedAt;
    }

    public void setPurchasedAt( Date purchasedAt ) {
        this.purchasedAt = purchasedAt;
    }

    public Date getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt( Date deliveredAt ) {
        this.deliveredAt = deliveredAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt( Date updatedAt ) {
        this.updatedAt = updatedAt;
    }

    public IncomingSupplyStatus getIncomingSupplyStatus() {
        return incomingSupplyStatus;
    }

    public void setIncomingSupplyStatus( IncomingSupplyStatus incomingSupplyStatus ) {
        this.incomingSupplyStatus = incomingSupplyStatus;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier( Supplier supplier ) {
        this.supplier = supplier;
    }

    public List<IncomingSupplyItem> getIncomingSupplyItems() {
        return incomingSupplyItems;
    }

    public void setIncomingSupplyItems( List<IncomingSupplyItem> incomingSupplyItems ) {
        this.incomingSupplyItems = incomingSupplyItems;
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
