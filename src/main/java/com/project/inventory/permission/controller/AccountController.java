package com.project.inventory.permission.controller;

import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.service.AccountService;
import com.project.inventory.webSecurity.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/account")
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveAccount(@RequestBody Account account){
        logger.info("{}", account.getEmail());
        accountService.saveUserAccount(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> loginAccount(@RequestBody Account account){
        return new ResponseEntity(userDetailsService.loadUserByUsername(account.getUsername()), HttpStatus.OK);
    }
}
