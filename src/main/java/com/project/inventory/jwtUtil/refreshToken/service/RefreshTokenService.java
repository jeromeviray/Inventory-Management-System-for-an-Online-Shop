package com.project.inventory.jwtUtil.refreshToken.service;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken saveRefreshToken( String refreshToken, Account account );

    RefreshToken getRefreshToken( String id );

    void removeRefreshToken( String id );

    Optional<RefreshToken> getFreshTokenByAccountId( int id );
}
