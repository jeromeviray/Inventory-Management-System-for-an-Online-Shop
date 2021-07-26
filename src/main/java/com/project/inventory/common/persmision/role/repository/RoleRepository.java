package com.project.inventory.common.persmision.role.repository;

import com.project.inventory.common.persmision.role.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findById(int id);
//    List<Role> findAllRole();

    Optional<Role> findByRoleName(String roleName);

}
