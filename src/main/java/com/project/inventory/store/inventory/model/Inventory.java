package com.project.inventory.store.inventory.model;

import com.project.inventory.store.product.model.Product;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "inventory")
public class Inventory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private int id;

    @Column(name = "stock")
    private int stock;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
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
        if (!(o instanceof Inventory)) return false;
        Inventory inventory = (Inventory) o;
        return getId() == inventory.getId() && getStock() == inventory.getStock() && Objects.equals(getProduct(), inventory.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getStock(), getProduct());
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", stock=" + stock +
                ", product=" + product +
                '}';
    }
}
