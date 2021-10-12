package com.project.inventory.common.permission.service.impl;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.exception.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service(value = "authenticatedUserImpl")
public class AuthenticatedUserImpl implements AuthenticatedUser {

   Logger logger = LoggerFactory.getLogger(AuthenticatedUserImpl.class);

   @Autowired
   private AccountService accountService;

    @Override
    public Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication;
    }

    @Override
    public Account getUserDetails() {
        Authentication authentication = getAuthentication();

        if ((authentication instanceof AnonymousAuthenticationToken)){

            throw new ForbiddenException(String.format("There is No Authenticated Account "));
        }
        String username = authentication.getName();
        return accountService.getAccountByUsername(username);
    }
}
