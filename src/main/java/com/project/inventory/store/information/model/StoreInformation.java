package com.project.inventory.store.information.model;

import com.project.inventory.store.product.model.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "store")
public class StoreInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "store_name", columnDefinition = "varchar(100)")
    private String storeName;

    @Column(name = "branch", columnDefinition = "varchar(100)", nullable = false)
    private String branch;

    @OneToMany(mappedBy = "storeInformation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName( String storeName ) {
        this.storeName = storeName;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch( String branch ) {
        this.branch = branch;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts( List<Product> products ) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "StoreInformation{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", branch='" + branch + '\'' +
                ", products=" + products +
                '}';
    }
}
