package com.project.inventory.common.permission.verificationCode.service.impl;

import com.project.inventory.common.permission.verificationCode.model.VerificationCode;
import com.project.inventory.common.permission.verificationCode.repository.VerificationCodeRepository;
import com.project.inventory.common.permission.verificationCode.service.VerificationCodeService;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.exception.invalid.InvalidException;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    Logger logger = LoggerFactory.getLogger( VerificationCodeServiceImpl.class );
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MailService mailService;

    @Override
    public void forgotPassword( String email ) {

        Account account = accountService.getAccountByEmail( email );
        if( account != null ) {
            VerificationCode verificationCode = new VerificationCode();
            Calendar date = Calendar.getInstance();
            String token = generateStrings();
            Date expiryDate = new Date( date.getTimeInMillis() + ( 5 * 60 * 1000 ) );
            verificationCode.setAccount( account );
            verificationCode.setExpiryDate( expiryDate );
            verificationCode.setToken( token );
            try {
                // send the token in the email
                String subject = "Here's the Forgot Password Token";
                String text = "Hello " + account.getUsername() + " here's your Forgot Password Token Code:\n" +
                        token + "\n" +
                        "\nNote: Your Forgot Password Token will be valid within Five Minutes.";
                mailService.sendEmail( account.getEmail(), subject, text );
                VerificationCode savedToken = verificationCodeRepository.save( verificationCode );

            } catch( Exception e ) {
                throw e;
            }
        }
    }
    @Override
    public void sendVerificationCode( Account account ) {

        if( account != null ) {
            VerificationCode verificationCode = new VerificationCode();
            Calendar date = Calendar.getInstance();
            String token = generateStrings();
            Date expiryDate = new Date( date.getTimeInMillis() + ( 5 * 60 * 1000 ) );
            verificationCode.setAccount( account );
            verificationCode.setExpiryDate( expiryDate );
            verificationCode.setToken( token );
            try {
                // send the token in the email
                String subject = "Here's the Verification Code";
                String text = "Hello " + account.getUsername() + " here's your Verification Code:\n" +
                        token + "\n" +
                        "\nNote: Your Verification Code will be valid within Five Minutes.";
                mailService.sendEmail( account.getEmail(), subject, text );
                verificationCodeRepository.save( verificationCode );

            } catch( Exception e ) {
                throw e;
            }

        }

    }
    @Override
    public VerificationCode getForgotPasswordByToken( String token ) {
        return verificationCodeRepository.findByToken( token ).orElseThrow( () -> new NotFoundException( "Your Forgot Password Token is not Found." ) );
    }

    @Override
    public VerificationCode validateToken( String token ) {
        VerificationCode verificationCode = getForgotPasswordByToken( token );
        if(isTokenExpired( verificationCode )){
            verificationCodeRepository.deleteVerificationCode( verificationCode.getId() );
            throw new InvalidException("Your Forgot Password has been Expired.");
        }else{
            return verificationCode;

        }
    }

    @Override
    public void deleteToken( String token ) {
        VerificationCode verificationCode = getForgotPasswordByToken( token );
        try{
            verificationCodeRepository.deleteVerificationCode( verificationCode.getId() );

        } catch( Exception e ) {
            throw e;
        }
    }

    @Override
    public boolean isTokenExpired( VerificationCode verificationCode ) {
        final Calendar cal = Calendar.getInstance();
        return verificationCode.getExpiryDate().before( cal.getTime() );
    }

    @Override
    public void verifyAccount( String code ) {
        //        verificationCodeService.validateToken( code );
        VerificationCode verification = getForgotPasswordByToken( code );

        if(isTokenExpired( verification )){
            deleteToken( verification.getToken() );
            throw new InvalidException("Your Verification Code has been Expired.");
        }else{
            accountService.verifyAccount( verification.getAccount().getId() );
        }
        deleteToken( verification.getToken() );
    }

    private String generateStrings() {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijk";
        Random rnd = new Random();
        StringBuilder randomString = new StringBuilder( 5 );
        for( int i = 0; i < 5; i++ ) {
            randomString.append( chars.charAt( rnd.nextInt( chars.length() ) ) );
        }
        return randomString.toString();
    }

}
