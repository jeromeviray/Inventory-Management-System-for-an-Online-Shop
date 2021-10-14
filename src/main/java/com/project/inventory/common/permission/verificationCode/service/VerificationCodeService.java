package com.project.inventory.common.permission.verificationCode.service;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.verificationCode.model.VerificationCode;

public interface VerificationCodeService {

    void forgotPassword( String email );

    void sendVerificationCode( Account account );

    VerificationCode validateToken( String token );

    void deleteToken( String token );

    VerificationCode getForgotPasswordByToken( String token );

    boolean isTokenExpired(VerificationCode verificationCode);

    void verifyAccount(String code);
}
