package com.project.inventory.common.persmision.role.service.impl;

import com.project.inventory.exception.account.RoleNotFoundException;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.common.persmision.role.model.RoleType;
import com.project.inventory.common.persmision.role.repository.RoleRepository;
import com.project.inventory.common.persmision.role.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "roleServiceImpl")
public class RoleServiceImpl implements RoleService {
    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role Not Found"));
    }

    @Override
    public void updateUserRole(Integer roleId, Role role) {
        Role savedRole = getRoleById(roleId);

        savedRole.setRoleName(role.getRoleName());

        saveRole(savedRole);
    }

    @Override
    public void deleteUserRole(Integer roleId) {
        Role role = getRoleById(roleId);
        roleRepository.delete(role);
    }

    @Override
    public List<Role> getUserRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByRoleName(RoleType role) {
        return roleRepository.findByRoleName(role.toString())
                .orElseThrow(() -> new RoleNotFoundException("Role Not Found!" +
                "PLease Check if there is a data in Database."));
    }
}