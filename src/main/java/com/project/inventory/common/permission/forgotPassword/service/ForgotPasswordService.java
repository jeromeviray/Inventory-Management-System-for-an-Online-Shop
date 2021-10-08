package com.project.inventory.common.permission.forgotPassword.service;

import com.project.inventory.common.permission.forgotPassword.model.ForgotPassword;

public interface ForgotPasswordService {

    void forgotPassword(  String email );

    ForgotPassword validateToken(String token);

    void deleteToken(String token);

    ForgotPassword getForgotPasswordByToken(String token);
}
