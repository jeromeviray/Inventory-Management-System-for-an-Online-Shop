package com.project.inventory.permission.service;

import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.model.ChangePassword;

public interface AccountService {
    Account getAccountById(int accountId);
    void saveUserAccount(Account account);
    Account getAccountByUsername(String username);
    void changePassword(ChangePassword changePassword);
    String changeUsername(int id, String username);
}
