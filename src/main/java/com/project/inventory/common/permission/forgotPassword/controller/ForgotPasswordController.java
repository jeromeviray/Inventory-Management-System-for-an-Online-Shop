package com.project.inventory.common.permission.forgotPassword.controller;

import com.project.inventory.common.permission.forgotPassword.model.ForgotPassword;
import com.project.inventory.common.permission.forgotPassword.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/v1/account" )
public class ForgotPasswordController {

    @Autowired
    private ForgotPasswordService forgotPasswordService;

    @RequestMapping( value = "/password/forgot", method = RequestMethod.GET )
    public ResponseEntity<?> forgotPassword( @RequestParam( value = "email", required = true ) String email ) {
        forgotPasswordService.forgotPassword( email );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/password/forgot/token", method = RequestMethod.GET )
    public ResponseEntity<?> validatedToken( @RequestParam( value = "token", required = true ) String token ) {
        ForgotPassword forgotPassword = forgotPasswordService.validateToken( token );

        Map<String, Object> response = new HashMap<>();
        response.put( "token", forgotPassword.getToken() );
        response.put( "accountId", forgotPassword.getAccount().getId() );
        return new ResponseEntity<>(response, HttpStatus.OK );
    }
}
