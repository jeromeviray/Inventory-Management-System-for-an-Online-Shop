package com.project.inventory.store.inventory.model;

import com.project.inventory.store.inventory.stock.model.Stock;
import com.project.inventory.store.inventory.stock.model.StockStatus;
import com.project.inventory.store.product.model.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table( name = "inventories" )
public class Inventory implements Serializable {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;

    @Enumerated( EnumType.STRING )
    @Column( name = "inventory_status" )
    private StockStatus status;

    @Column( name = "threshold_stock" )
    private int threshold;

    @OneToMany( mappedBy = "inventory" )
    private List<Stock> stocks;

    @OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn( name = "product_id", nullable = false )
    private Product product;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public StockStatus getStatus() {
        return status;
    }

    public void setStatus( StockStatus status ) {
        this.status = status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct( Product product ) {
        this.product = product;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold( int threshold ) {
        this.threshold = threshold;
    }

    public List<Stock> getStock() {
        return stocks;
    }

    public void setStock( List<Stock> stocks ) {
        this.stocks = stocks;
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
