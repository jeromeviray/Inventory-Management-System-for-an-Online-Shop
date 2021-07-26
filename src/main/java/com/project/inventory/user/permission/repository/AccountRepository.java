package com.project.inventory.user.permission.repository;

import com.project.inventory.user.permission.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findById(int accountId);
    Account findByUsername(String username);

}
