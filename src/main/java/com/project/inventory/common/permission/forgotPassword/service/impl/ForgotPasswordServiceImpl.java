package com.project.inventory.common.permission.forgotPassword.service.impl;

import com.project.inventory.common.permission.forgotPassword.model.ForgotPassword;
import com.project.inventory.common.permission.forgotPassword.repository.ForgotPasswordRepository;
import com.project.inventory.common.permission.forgotPassword.service.ForgotPasswordService;
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
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
    Logger logger = LoggerFactory.getLogger( ForgotPasswordServiceImpl.class );
    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private MailService mailService;

    @Override
    public void forgotPassword( String email ) {

        Account account = accountService.getAccountByEmail( email );
        if( account != null ) {
            ForgotPassword forgotPassword = new ForgotPassword();
            Calendar date = Calendar.getInstance();
            String token = generateStrings();
            Date expiryDate = new Date( date.getTimeInMillis() + ( 5 * 60 * 1000 ) );
            forgotPassword.setAccount( account );
            forgotPassword.setExpiryDate( expiryDate );
            forgotPassword.setToken( token );
            try {
                // send the token in the email
                String subject = "Here's the Forgot Password Token";
                String text = "Hello " + account.getUsername() + " here's your Forgot Password Token Code:\n" +
                        token + "\n" +
                        "\nNote: Your Forgot Password Token will be valid within Five Minutes.";
                mailService.sendEmail( account.getEmail(), subject, text );
                ForgotPassword savedToken = forgotPasswordRepository.save( forgotPassword );

            } catch( Exception e ) {
                throw e;
            }

        }

    }

    @Override
    public ForgotPassword getForgotPasswordByToken( String token ) {
        return forgotPasswordRepository.findByToken( token ).orElseThrow( () -> new NotFoundException( "Your Forgot Password Token is not Found." ) );
    }

    @Override
    public ForgotPassword validateToken( String token ) {
        ForgotPassword forgotPassword = getForgotPasswordByToken( token );
        if(isTokenExpired( forgotPassword )){
            forgotPasswordRepository.deleteForgotPassword( forgotPassword.getId() );
            throw new InvalidException("Your Forgot Password has been Expired.");
        }else{
            return forgotPassword;

        }
    }

    @Override
    public void deleteToken( String token ) {
        ForgotPassword forgotPassword = getForgotPasswordByToken( token );
        try{
            forgotPasswordRepository.deleteForgotPassword( forgotPassword.getId() );

        } catch( Exception e ) {
            throw e;
        }
    }

    private boolean isTokenExpired( ForgotPassword forgotPassword ) {
        final Calendar cal = Calendar.getInstance();
        return forgotPassword.getExpiryDate().before( cal.getTime() );
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
