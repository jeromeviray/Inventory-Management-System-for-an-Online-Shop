package com.project.inventory.common.persmision.model;

import com.project.inventory.common.persmision.role.model.Role;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "account")
@Transactional
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column( name = "username", unique = true, nullable = false, length = 30 )
    private String username;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column( name = "email", unique = true, nullable = false, length = 100 )
    private String email;

    @CreationTimestamp
    @Column(name = " created_at ", nullable = false)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated;

    @Column(name = "is_enabled", columnDefinition = "TINYINT(1) ", nullable = false)
    private boolean isEnabled = true;

    @Column(name = "is_locked", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isLocked = true;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
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
        return getId() == account.getId() && isEnabled() == account.isEnabled() && isLocked() == account.isLocked() && Objects.equals(getUsername(), account.getUsername()) && Objects.equals(getPassword(), account.getPassword()) && Objects.equals(getEmail(), account.getEmail()) && Objects.equals(getCreated(), account.getCreated()) && Objects.equals(getUpdated(), account.getUpdated()) && Objects.equals(getRoles(), account.getRoles());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUsername(), getPassword(), getEmail(), getCreated(), getUpdated(), isEnabled(), isLocked(), getRoles());
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                ", updated=" + updated +
                ", isEnabled=" + isEnabled +
                ", isLocked=" + isLocked +
                ", roles=" + roles +
                '}';
    }
}
