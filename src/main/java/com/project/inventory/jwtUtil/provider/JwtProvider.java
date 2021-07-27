package com.project.inventory.jwtUtil.provider;

import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.userdetails.User;

public interface JwtProvider {
    String accessToken(User user);
    String refreshToken(User user);
    DecodedJWT verifier(String token);
    String getSubjectClaim(String token);
    String[] getRoles(String token);
}
