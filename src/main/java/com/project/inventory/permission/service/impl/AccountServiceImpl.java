package com.project.inventory.permission.service.impl;

import com.project.inventory.exception.account.AccountNotFoundException;
import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.repository.AccountRepository;
import com.project.inventory.permission.role.model.Role;
import com.project.inventory.permission.role.model.RoleType;
import com.project.inventory.permission.role.service.RoleService;
import com.project.inventory.permission.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountServiceImpl implements AccountService {
    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleService roleService;


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

        accountRepository.save(account);
    }

    @Override
    public Account getAccountByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
