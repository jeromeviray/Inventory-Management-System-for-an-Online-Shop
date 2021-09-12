package com.project.inventory.common.permission.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.inventory.common.permission.role.model.Role;
import com.project.inventory.common.permission.role.model.RoleDto;

import java.util.Date;
import java.util.Set;

public class AccountInfoDto {
    private int id;
    private String username;
    private String email;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date created;
    private Set<RoleDto> roles;
    private boolean isEnabled;
    private boolean isLocked;

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

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

    public Set<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles( Set<RoleDto> roles ) {
        this.roles = roles;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled( boolean enabled ) {
        isEnabled = enabled;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked( boolean locked ) {
        isLocked = locked;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
