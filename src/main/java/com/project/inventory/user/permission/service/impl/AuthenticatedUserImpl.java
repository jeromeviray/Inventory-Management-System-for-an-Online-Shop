package com.project.inventory.user.permission.service.impl;

import com.project.inventory.user.permission.model.Account;
import com.project.inventory.user.permission.service.AccountService;
import com.project.inventory.user.permission.service.AuthenticatedUser;
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
//        if (authentication == null){
//            throw new UsernameNotFoundException(String.format("There is No Authenticated Account "));
//        }
        return authentication;
    }

    @Override
    public Account getUserDetails() {
        Authentication authentication = getAuthentication();

        if ((authentication instanceof AnonymousAuthenticationToken)){

            throw new UsernameNotFoundException(String.format("There is No Authenticated Account "));
        }
        logger.info(String.format("Current user with username " +authentication.getName()));
        String username = authentication.getName();
        return accountService.getAccountByUsername(username);
    }
}
