package com.project.inventory.webSecurity.impl;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.common.persmision.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {
    Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.getAccountByUsername(username);
        if(account == null ){
            logger.info( "Account {} not found!", username );
            throw new UsernameNotFoundException("Account Not Found");
        }

        return new User(
                        account.getUsername(),
                        account.getPassword(),
                        account.isEnabled(),
                        true,
                        true,
                        account.isLocked(),
                        getGrantedAuthorities(account) );
    }

    private List<GrantedAuthority> getGrantedAuthorities(Account account ){
        List<GrantedAuthority> authorities = new ArrayList<>();
        for( Role role : account.getRoles() ) {
            authorities.add( new SimpleGrantedAuthority("ROLE_"+ role.getRoleName()));
        }

        return authorities;
    }
}
