package com.project.inventory.permission.role.service.impl;

import com.project.inventory.exception.account.RoleNotFoundException;
import com.project.inventory.permission.role.model.Role;
import com.project.inventory.permission.role.repository.RoleRepository;
import com.project.inventory.permission.role.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Role getRoleById(int id) {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException("Role Not Found"));
    }
}