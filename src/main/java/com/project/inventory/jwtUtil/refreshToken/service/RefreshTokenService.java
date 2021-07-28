package com.project.inventory.jwtUtil.refreshToken.service;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;

public interface RefreshTokenService {

    RefreshToken saveRefreshToken( String refreshToken, Account account );

    RefreshToken getRefreshToken( String id );

    void removeRefreshToken( String id );
}
