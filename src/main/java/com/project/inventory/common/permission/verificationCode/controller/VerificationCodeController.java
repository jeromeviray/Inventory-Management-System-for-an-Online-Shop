package com.project.inventory.common.permission.verificationCode.controller;

import com.project.inventory.common.permission.verificationCode.model.VerificationCode;
import com.project.inventory.common.permission.verificationCode.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/v1/account" )
public class VerificationCodeController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @RequestMapping( value = "/password/forgot", method = RequestMethod.GET )
    public ResponseEntity<?> forgotPassword( @RequestParam( value = "email", required = true ) String email ) {
        verificationCodeService.forgotPassword( email );
        return new ResponseEntity<>( HttpStatus.OK );
    }

    @RequestMapping( value = "/password/forgot/token", method = RequestMethod.GET )
    public ResponseEntity<?> validatedToken( @RequestParam( value = "token", required = true ) String token ) {
        VerificationCode verificationCode = verificationCodeService.validateToken( token );

        Map<String, Object> response = new HashMap<>();
        response.put( "token", verificationCode.getToken() );
        response.put( "accountId", verificationCode.getAccount().getId() );
        return new ResponseEntity<>(response, HttpStatus.OK );
    }
    @RequestMapping(value = "/verification/{code}", method = RequestMethod.GET)
    public ResponseEntity<?> verifyAccount( @PathVariable String code ){
        verificationCodeService.verifyAccount( code );
        return new ResponseEntity<>(HttpStatus.OK );
    }
}
