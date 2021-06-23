package com.project.inventory.permission.service;

import com.project.inventory.permission.model.Account;
import org.springframework.security.core.Authentication;

public interface AuthenticatedUser {
    public Authentication getAuthentication();

    public Account getUserDetails();
}
