package com.project.inventory.permission.service.impl;

import com.project.inventory.exception.account.AccountNotFoundException;
import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.repository.AccountRepository;
import com.project.inventory.permission.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccountById(int accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId);
    }
}
