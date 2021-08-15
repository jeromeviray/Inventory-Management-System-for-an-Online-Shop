package com.project.inventory.jwtUtil.provider;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshTokenResponse;

import java.io.IOException;

public interface JwtProvider {
    String accessToken(Account account);
    Object refreshToken( Account account);
    DecodedJWT verifier(String token);
    String getSubjectClaim(String token);
    String[] getRoles(String token);
    RefreshTokenResponse refreshToken( RefreshToken refreshToken) throws IOException;
}
