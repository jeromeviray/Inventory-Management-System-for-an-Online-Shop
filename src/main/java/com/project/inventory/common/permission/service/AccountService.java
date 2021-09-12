package com.project.inventory.common.permission.service;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.model.AccountDto;
import com.project.inventory.common.permission.model.ChangePassword;

import javax.security.auth.login.AccountLockedException;

public interface AccountService {
    Account getAccountById( int accountId );

    void saveUserAccount( Account account );

    Account saveEmployeeAccount( String username, String password, String email );

    Account getAccountByUsername( String username );

    void changePassword( ChangePassword changePassword );

    String changeUsername( int id, String username );

    AccountDto convertEntityToDto( Account account );

    Account convertDtoToEntity( AccountDto accountDto );

    Boolean isEnabled( Account account ) throws AccountLockedException;

    Boolean isLocked( Account account ) throws AccountLockedException;

    Boolean isEnabledAndLocked( Account account ) throws AccountLockedException;

    void deleteAccount(int id);
}
