package com.project.inventory.permission.role.repository;

import com.project.inventory.permission.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findById(int id);
//    List<Role> findAllRole();

}
