package com.project.inventory.common.user.repository;

import com.project.inventory.common.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);
    Optional<User> findByAccountId(int accountId);
}
