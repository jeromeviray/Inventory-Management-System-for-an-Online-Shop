package com.project.inventory.user.permission.service;

import com.project.inventory.user.permission.model.Account;
import org.springframework.security.core.Authentication;

public interface AuthenticatedUser {
    public Authentication getAuthentication();

    public Account getUserDetails();
}
