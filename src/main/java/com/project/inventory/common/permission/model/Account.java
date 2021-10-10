package com.project.inventory.common.permission.model;

import com.project.inventory.common.permission.role.model.Role;
import com.project.inventory.webSecurity.oauth2.AuthProvider;
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
@Table(name = "accounts")
@Transactional
public class Account implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column( name = "username", unique = true, nullable = false, length = 30 )
    private String username;

    @Column(name = "password", length = 200)
    private String password;

    @Column( name = "email", unique = true, nullable = false, length = 100 )
    private String email;

    @CreationTimestamp
    @Column(name = " created_at ", nullable = false)
    private Date created;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Date updated;

    @Column(name = "is_not_banned", columnDefinition = "TINYINT(1) default 1", nullable = false)
    private boolean isNotBanned = true;

    @Column(name = "is_not_Deleted", columnDefinition = "TINYINT(1) default 1", nullable = false)
    private boolean isNotDeleted = true;

    @Enumerated(EnumType.STRING)
    @Column(name ="auth_provider", nullable = false)
    private AuthProvider authProvider;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = " user_privileges ",
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

    public boolean isNotBanned() {
        return isNotBanned;
    }

    public void setNotBanned( boolean notBanned ) {
        isNotBanned = notBanned;
    }

    public boolean isNotDeleted() {
        return isNotDeleted;
    }

    public void setNotDeleted( boolean notDeleted ) {
        isNotDeleted = notDeleted;
    }

    public AuthProvider getAuthProvider() {
        return authProvider;
    }

    public void setAuthProvider( AuthProvider authProvider ) {
        this.authProvider = authProvider;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
