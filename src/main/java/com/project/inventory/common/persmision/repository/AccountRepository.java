package com.project.inventory.common.persmision.repository;

import com.project.inventory.common.persmision.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findById(int accountId);
    Account findByUsername(String username);
    Optional<Account> findByEmail( String email);

}
