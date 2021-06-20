package com.project.inventory.permission.role.service;

import com.project.inventory.permission.role.model.Role;

public interface RoleService {

    void saveRole(Role role);
    Role getRoleById(int id);
}
