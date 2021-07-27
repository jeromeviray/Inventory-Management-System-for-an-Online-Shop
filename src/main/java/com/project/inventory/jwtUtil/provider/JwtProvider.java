package com.project.inventory.jwtUtil.provider;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.inventory.common.persmision.model.Account;
import org.springframework.security.core.userdetails.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface JwtProvider {
    String accessToken(Account account);
    String refreshToken(Account account);
    DecodedJWT verifier(String token);
    String getSubjectClaim(String token);
    String[] getRoles(String token);
    void verifierRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException;
}
