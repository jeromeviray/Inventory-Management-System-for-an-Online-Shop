package com.project.inventory.common.permission.service;

import com.project.inventory.common.permission.verificationCode.model.ResetPassword;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.model.AccountDto;
import com.project.inventory.common.permission.model.ChangePassword;
import com.project.inventory.common.permission.role.model.RoleType;
import com.project.inventory.common.user.model.RequestUserAccount;
import com.project.inventory.common.user.model.UserAccount;

import javax.security.auth.login.AccountLockedException;

public interface AccountService {
    Account getAccountById( int accountId );

    void saveUserAccount( UserAccount userAccount );

    Account saveEmployeeAccount( String username, String password, String email, RoleType roleType );

    Account getAccountByUsername( String username );

    Account getAccountByEmail( String email );

    void changePassword( ChangePassword changePassword );

    String changeUsername( int id, String username );

    AccountDto convertEntityToDto( Account account );

    Account convertDtoToEntity( AccountDto accountDto );

    Boolean isNotBanned( Account account ) throws AccountLockedException;

    Boolean isNotDeleted( Account account ) throws AccountLockedException;

    Boolean isNotBannedAndDeleted( Account account ) throws AccountLockedException;

    void deleteAccount( int id );

    void banAccount( int id);

    Account updateAccount( int id, RequestUserAccount account );

    void resetPassword( ResetPassword resetPassword );

    void verifyAccount(int accountId);
}
