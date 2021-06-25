package com.project.inventory.permission.service.impl;

import com.project.inventory.exception.account.AccountNotFoundException;
import com.project.inventory.exception.account.PasswordException;
import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.model.ChangePassword;
import com.project.inventory.permission.repository.AccountRepository;
import com.project.inventory.permission.role.model.Role;
import com.project.inventory.permission.role.model.RoleType;
import com.project.inventory.permission.role.service.RoleService;
import com.project.inventory.permission.service.AccountService;
import com.project.inventory.user.information.model.UserInformation;
import com.project.inventory.user.information.service.UserInformationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service(value = "accountServiceImpl")
public class AccountServiceImpl implements AccountService {
    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserInformationService userInformationService;


    @Override
    public Account getAccountById(int accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId);
    }


    @Override
    public void saveUserAccount(Account account) {
        account.setPassword( passwordEncoder.encode(account.getPassword()) );

        Role role = roleService.getRoleByRoleName(RoleType.CUSTOMER);
        Set<Role> authority = new HashSet<>();
        authority.add(role);
        logger.info("{}", role.getRoleName());

        account.setRoles(authority);

        Account savedAccount = accountRepository.save(account);

        userInformationService.saveUserInformation(account, new UserInformation());
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public void changePassword(ChangePassword changePassword) {
        Account account = getAccountById(changePassword.getId());
        if(!comparePassword(account, changePassword.getCurrentPassword())){
            throw new PasswordException("Mismatch Current Password. Please Try Again!");
        }else if(!changePassword.getPassword().equals(changePassword.getConfirmPassword())){
            throw new PasswordException("Not match New Password. Please Try Again!");
        }
        account.setPassword(passwordEncoder.encode(changePassword.getPassword()));
        accountRepository.save(account);
    }

    @Override
    public String changeUsername(int id, String username) {
        Account account = getAccountById(id);
        account.setUsername(username);

        Account updatedUsernameAccount = accountRepository.save(account);

        return updatedUsernameAccount.getUsername();
    }

    protected boolean comparePassword(Account account, String currentPassword){
        logger.info("{}", "Compare Current Password: "
                +passwordEncoder.matches(currentPassword, account.getPassword()));

        return passwordEncoder.matches(currentPassword, account.getPassword());
    }
}
