package com.project.inventory.common.persmision.service;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.model.AccountDto;
import com.project.inventory.common.persmision.model.ChangePassword;

public interface AccountService {
    Account getAccountById(int accountId);
    void saveUserAccount(Account account);
    Account getAccountByUsername(String username);
    void changePassword(ChangePassword changePassword);
    String changeUsername(int id, String username);
    AccountDto convertEntityToDto(Account account);
    Account convertDtoToEntity(AccountDto accountDto);
}
