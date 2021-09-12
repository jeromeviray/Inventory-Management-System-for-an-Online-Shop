package com.project.inventory.exception;

import javax.naming.AuthenticationException;

public class OAuth2AuthenticationException extends AuthenticationException {

    public OAuth2AuthenticationException(String msg) {
        super(msg);
    }
}
