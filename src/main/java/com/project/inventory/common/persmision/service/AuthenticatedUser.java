package com.project.inventory.common.persmision.service;

import com.project.inventory.common.persmision.model.Account;
import org.springframework.security.core.Authentication;

public interface AuthenticatedUser {
    public Authentication getAuthentication();

    public Account getUserDetails();
}
