package com.project.inventory.user.permission.role.service;

import com.project.inventory.user.permission.role.model.Role;
import com.project.inventory.user.permission.role.model.RoleType;

import java.util.List;

public interface RoleService {

    void saveRole(Role role);
    Role getRoleById(Integer id);

    void updateUserRole(Integer roleId, Role role);

    void deleteUserRole(Integer roleId);

    List<Role> getUserRoles();

    Role getRoleByRoleName(RoleType role);
}
