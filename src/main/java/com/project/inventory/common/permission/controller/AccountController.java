package com.project.inventory.common.permission.controller;

import com.project.inventory.common.permission.verificationCode.model.ResetPassword;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.model.ChangePassword;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.common.user.model.UserAccount;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping( value = "api/v1/users/account" )
public class AccountController {
    Logger logger = LoggerFactory.getLogger( AccountController.class );
    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticatedUser authenticatedUser;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtProvider jwtProvider;

    @RequestMapping( value = "/register", method = RequestMethod.POST )
    public ResponseEntity<?> saveAccount( @RequestBody UserAccount userAccount ) {
        accountService.saveUserAccount( userAccount );
        return new ResponseEntity( HttpStatus.OK );
    }

    @RequestMapping( value = "/change/password", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<?> changePassword( @RequestBody ChangePassword changePassword ) {
        accountService.changePassword( changePassword );
        return new ResponseEntity( HttpStatus.OK );
    }
    @PreAuthorize( "hasRole('ROLE_SUPER_ADMIN')" )
    @RequestMapping( value = "/ban/{id}", method = RequestMethod.DELETE )
    public ResponseEntity<?> banAccount( @PathVariable int id ) {
        accountService.banAccount( id );
        return new ResponseEntity( HttpStatus.OK );
    }

    @RequestMapping( value = "/id/{id}", method = RequestMethod.POST )
    public ResponseEntity<?> getAccountById( @PathVariable int id ) {
        return new ResponseEntity( accountService.getAccountById( id ), HttpStatus.OK );
    }

    @RequestMapping( value = "/username/{username}", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<?> getAccountById( @PathVariable String username ) {
        return new ResponseEntity( accountService.getAccountByUsername( username ), HttpStatus.OK );
    }

    @RequestMapping( value = "/change/username", method = RequestMethod.POST )
    @ResponseBody
    public ResponseEntity<?> changeUsername( @RequestBody Account account ) {
        return new ResponseEntity( accountService.changeUsername( account.getId(), account.getUsername() ), HttpStatus.OK );
    }

    @RequestMapping( value = "/token/refresh", method = RequestMethod.POST )
    public ResponseEntity<?> tokenRefresh( @RequestBody RefreshToken refreshToken ) throws IOException {
        return new ResponseEntity( jwtProvider.validateAndGetAccessToken( refreshToken ), HttpStatus.OK );
    }

    @RequestMapping( value = "/reset/password", method = RequestMethod.POST )
    public ResponseEntity<?> resetPassword( @RequestBody ResetPassword resetPassword ) throws IOException {
        accountService.resetPassword( resetPassword );
        return new ResponseEntity( HttpStatus.OK );
    }
}
