package com.project.inventory.store.cart.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.common.permission.model.Account;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    @JsonIgnore
    private Account account;

    @OneToMany(mappedBy = "cart", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"cart"})
    private List<CartItem> cartItems;

    @Column(name = "checkout_expiration", columnDefinition="DATETIME DEFAULT NULL")
    private Date checkExpiration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public Date getCheckExpiration() {
        return checkExpiration;
    }

    public void setCheckExpiration( Date checkExpiration ) {
        this.checkExpiration = checkExpiration;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", account=" + account +
                ", cartItems=" + cartItems +
                '}';
    }
}
