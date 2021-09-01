package com.project.inventory.common.permission.role.model;

public class RoleDto {
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName( String roleName ) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleDto{" +
                "roleName='" + roleName + '\'' +
                '}';
    }
}
