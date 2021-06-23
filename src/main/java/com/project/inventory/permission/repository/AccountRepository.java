package com.project.inventory.permission.repository;

import com.project.inventory.permission.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findById(int accountId);
    Account findByUsername(String username);

}
