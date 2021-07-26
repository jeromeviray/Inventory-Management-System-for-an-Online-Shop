package com.project.inventory.common.persmision.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class AccountInfoDto {
    private String username;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "AccountInfoDto{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", created=" + created +
                '}';
    }
}
