package com.project.inventory.common.persmision.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.model.ChangePassword;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.common.persmision.service.AuthenticatedUser;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.provider.impl.JwtProviderImpl;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/v1/account")
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Autowired
    private JwtProvider jwtProvider;


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> saveAccount(@RequestBody Account account){
        accountService.saveUserAccount(account);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/change/password", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword){
        accountService.changePassword(changePassword);
        return new ResponseEntity(HttpStatus.OK);
    }

    @RequestMapping(value = "/id/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> getAccountById(@PathVariable int id){
        return new ResponseEntity(accountService.getAccountById(id), HttpStatus.OK);
    }

    @RequestMapping(value = "/username/{username}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> getAccountById(@PathVariable String username){
        return new ResponseEntity(accountService.getAccountByUsername(username), HttpStatus.OK);
    }

    @RequestMapping(value = "/change/username", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> changeUsername(@RequestBody Account account){
        return new ResponseEntity(accountService.changeUsername(account.getId(), account.getUsername()), HttpStatus.OK);
    }

    @RequestMapping(value = "/token/refresh", method = RequestMethod.POST)
    public ResponseEntity<?> tokenRefresh( @RequestBody RefreshToken refreshToken, HttpServletResponse response ) throws IOException {
        return new ResponseEntity(jwtProvider.refreshToken(refreshToken, response), HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticated")
    public ResponseEntity<?> getAuthenticatedUser(){
        Account account = authenticatedUser.getUserDetails();
        return new ResponseEntity(accountService.convertEntityToDto(account), HttpStatus.OK);
    }
}
