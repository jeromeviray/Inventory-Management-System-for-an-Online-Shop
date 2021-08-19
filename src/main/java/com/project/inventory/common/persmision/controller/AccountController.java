package com.project.inventory.common.persmision.controller;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.model.ChangePassword;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.common.persmision.service.AuthenticatedUser;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1/account")
public class AccountController {
    Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticatedUser authenticatedUser;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

//    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
//    public ResponseEntity<?> authentication(@RequestBody Account account){
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        account.getUsername(),
//                        account.getPassword()
//                )
//        );
//        logger.info( "{}",authentication );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        String token = jwtProvider
//                .accessToken(accountService.getAccountByUsername( authentication.getName() ));
//
//        return new ResponseEntity( HttpStatus.OK);
//    }

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
    public ResponseEntity<?> tokenRefresh( @RequestBody RefreshToken refreshToken ) throws IOException {
        return new ResponseEntity(jwtProvider.validateAndGetAccessToken( refreshToken ), HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticated")
    public ResponseEntity<?> getAuthenticatedUser(){
        Account account = authenticatedUser.getUserDetails();
        return new ResponseEntity(accountService.convertEntityToDto(account), HttpStatus.OK);
    }
}
