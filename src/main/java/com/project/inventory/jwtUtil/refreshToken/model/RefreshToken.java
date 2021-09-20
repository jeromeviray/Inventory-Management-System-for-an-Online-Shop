package com.project.inventory.jwtUtil.refreshToken.model;

import com.project.inventory.common.permission.model.Account;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue( generator = "UUID" )
    @GenericGenerator( name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator" )
    @Column( name = "id", updatable = false, nullable = false, unique = true )
    private String id;

    @Column( unique = true, nullable = false )
    private String refreshToken;

    @OneToOne
    @JoinColumn(name =" account_id ", nullable = false, unique = true, updatable = false)
    private Account account;

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken( String refreshToken ) {
        this.refreshToken = refreshToken;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount( Account account ) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "RefreshToken{" +
                "id='" + id + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", account=" + account +
                '}';
    }
}
