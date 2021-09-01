package com.project.inventory.common.permission.service;

import com.project.inventory.common.permission.model.Account;
import org.springframework.security.core.Authentication;

public interface AuthenticatedUser {
    public Authentication getAuthentication();

    public Account getUserDetails();
}
