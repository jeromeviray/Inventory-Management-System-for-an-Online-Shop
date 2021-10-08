package com.project.inventory.common.permission.forgotPassword.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.inventory.common.permission.model.Account;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="account_forgot_password")
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "forgot_password_token")
    private String token;

    @Column(name = "expiration_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date expiryDate;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount( Account account ) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
       this.token = token;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate( Date expiryDate ) {
        this.expiryDate = expiryDate;
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
