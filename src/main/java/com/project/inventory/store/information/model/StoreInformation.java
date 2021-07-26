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

    @Column(name = "store_name", columnDefinition = "varchar(100)", nullable = false)
    private String storeName;

    @Column(name = "address_location", columnDefinition = "varchar(100)", nullable = false, unique = true)
    private String location;

    @OneToMany(mappedBy = "storeInformation", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "StoreInformation{" +
                "id=" + id +
                ", storeName='" + storeName + '\'' +
                ", location='" + location + '\'' +
                '}';
    }
}
