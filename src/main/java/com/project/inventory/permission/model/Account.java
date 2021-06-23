package com.project.inventory.permission.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.customer.address.model.CustomerAddress;
import com.project.inventory.order.shoppingOrder.model.ShoppingOrder;
import com.project.inventory.permission.role.model.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "account")
@Transactional
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private int id;

    @Column( name = "username", unique = true, nullable = false, length = 30 )
    private String username;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column( name = "email", unique = true, nullable = false, length = 100 )
    private String email;

    @CreationTimestamp
    @Column(name = " created_at ")
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updated;

    @Column(name = "is_deleted")
    private boolean deleted;

    @ManyToMany
    @JoinTable(name = " user_privilege ",
                joinColumns = { @JoinColumn ( name = "account_id")},
                inverseJoinColumns = { @JoinColumn (name = "role_id")})
    private Set<Role> roles = new HashSet<>();


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return getId() == account.getId() && isDeleted() == account.isDeleted() && Objects.equals(getUsername(), account.getUsername()) && Objects.equals(getPassword(), account.getPassword()) && Objects.equals(getEmail(), account.getEmail()) && Objects.equals(getCreated(), account.getCreated()) && Objects.equals(getUpdated(), account.getUpdated()) && Objects.equals(getRoles(), account.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getEmail(), getCreated(), getUpdated(), isDeleted(), getRoles());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
