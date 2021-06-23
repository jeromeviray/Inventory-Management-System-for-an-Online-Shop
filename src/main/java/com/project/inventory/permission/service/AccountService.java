package com.project.inventory.permission.service;

import com.project.inventory.permission.model.Account;

import java.util.Optional;

public interface AccountService {
    Account getAccountById(int accountId);
    void saveUserAccount(Account account);
    Account getAccountByUsername(String username);

}
