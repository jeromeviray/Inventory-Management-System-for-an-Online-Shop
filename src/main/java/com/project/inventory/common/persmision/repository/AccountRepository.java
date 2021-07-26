package com.project.inventory.common.persmision.repository;

import com.project.inventory.common.persmision.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findById(int accountId);
    Account findByUsername(String username);

}
