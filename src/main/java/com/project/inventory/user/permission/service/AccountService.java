package com.project.inventory.user.permission.service;

import com.project.inventory.user.permission.model.Account;
import com.project.inventory.user.permission.model.AccountDto;
import com.project.inventory.user.permission.model.ChangePassword;

public interface AccountService {
    Account getAccountById(int accountId);
    void saveUserAccount(Account account);
    Account getAccountByUsername(String username);
    void changePassword(ChangePassword changePassword);
    String changeUsername(int id, String username);
    AccountDto convertEntityToDto(Account account);
    Account convertDtoToEntity(AccountDto accountDto);
}
