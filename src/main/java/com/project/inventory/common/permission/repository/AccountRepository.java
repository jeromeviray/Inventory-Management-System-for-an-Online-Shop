package com.project.inventory.common.permission.repository;

import com.project.inventory.common.permission.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findById( int accountId );

    Account findByUsername( String username );

    Optional<Account> findByEmail( String email );

}