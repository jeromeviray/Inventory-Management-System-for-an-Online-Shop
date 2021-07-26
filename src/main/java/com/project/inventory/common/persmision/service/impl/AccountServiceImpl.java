package com.project.inventory.common.persmision.service.impl;

import com.project.inventory.exception.account.AccountNotFoundException;
import com.project.inventory.exception.account.PasswordException;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.model.AccountDto;
import com.project.inventory.common.persmision.model.ChangePassword;
import com.project.inventory.common.persmision.repository.AccountRepository;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.common.persmision.role.model.RoleType;
import com.project.inventory.common.persmision.role.service.RoleService;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.common.user.model.User;
import com.project.inventory.common.user.service.UserService;
import org.modelmapper.ModelMapper;
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
    private UserService userService;
    @Autowired
    private ModelMapper mapper;


    @Override
    public Account getAccountById(int accountId) throws AccountNotFoundException {
        return accountRepository.findById(accountId);
    }


    @Override
    public void saveUserAccount(Account account) {
        logger.info("{}", account.getPassword());
        account.setPassword( passwordEncoder.encode(account.getPassword()) );

        Role role = roleService.getRoleByRoleName(RoleType.CUSTOMER);
        Set<Role> authority = new HashSet<>();
        authority.add(role);
        logger.info("{}", role.getRoleName());

        account.setRoles(authority);

        Account savedAccount = accountRepository.save(account);

        userService.saveUserInformation(account, new User());
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

    @Override
    public AccountDto convertEntityToDto(Account account) {
        return mapper.map(account, AccountDto.class);
    }

    @Override
    public Account convertDtoToEntity(AccountDto accountDto) {
        return mapper.map(accountDto, Account.class);

    }

    protected boolean comparePassword(Account account, String currentPassword){
        logger.info("{}", "Compare Current Password: "
                +passwordEncoder.matches(currentPassword, account.getPassword()));

        return passwordEncoder.matches(currentPassword, account.getPassword());
    }
}
