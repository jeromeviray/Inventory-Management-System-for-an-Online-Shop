package com.project.inventory.permission.controller;

import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.model.ChangePassword;
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

    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword){
        accountService.changePassword(changePassword);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> getAccountById(@PathVariable int id){
        return new ResponseEntity(accountService.getAccountById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> getAccountById(@PathVariable String username){
        return new ResponseEntity(accountService.getAccountByUsername(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/change/username", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changeUsername(@RequestBody Account account){
        return new ResponseEntity(accountService.changeUsername(account.getId(), account.getUsername()), HttpStatus.OK);
    }
}
