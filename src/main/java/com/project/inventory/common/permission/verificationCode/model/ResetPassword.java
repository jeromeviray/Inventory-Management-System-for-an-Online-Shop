package com.project.inventory.common.permission.verificationCode.model;

public class ResetPassword {
    private int accountId;
    private String token;
    private String password;
    private String confirmPassword;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId( int accountId ) {
        this.accountId = accountId;
    }

    public String getToken() {
        return token;
    }

    public void setToken( String token ) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword( String password ) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword( String confirmPassword ) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
