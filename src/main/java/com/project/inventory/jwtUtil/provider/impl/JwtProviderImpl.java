package com.project.inventory.jwtUtil.provider.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.exception.NotFoundException;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshTokenResponse;
import com.project.inventory.jwtUtil.refreshToken.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InvalidClassException;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtProviderImpl implements JwtProvider {
    Logger logger = LoggerFactory.getLogger(JwtProviderImpl.class);

    private static final Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 1hr 3600000 or 10*60*1000 10 minutes duration

    private static final Date refreshTokenExpiresAt = new Date(System.currentTimeMillis() + 604800000);

    private String SECRET_KEY = Base64.getEncoder().encodeToString("osqda#x!@jkd!@hda2".getBytes());

    @Autowired
    private AccountService accountService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    public Algorithm getClaimSecretToken(){
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        return algorithm;
    }

    @Override
    public String accessToken(Account account) {

        return JWT.create()
                .withSubject(account.getUsername())
                .withExpiresAt(accessTokenExpiresAt)
                .withIssuedAt(new Date())
                .withClaim("roles", account.getRoles()
                        .stream().map(Role::getRoleName)
                        .collect(Collectors.toList()))
                .sign(getClaimSecretToken());

    }

    @Override
    public String refreshToken(Account account) {
        return JWT.create()
                .withSubject(account.getUsername())
                .withExpiresAt(refreshTokenExpiresAt)
                .withIssuedAt(new Date())
                .withClaim("roles", account.getRoles()
                        .stream().map(Role::toString)
                        .collect(Collectors.toList()))
                .sign(getClaimSecretToken());
    }

    @Override
    public DecodedJWT verifier(String token) {
        JWTVerifier verifier = JWT.require(getClaimSecretToken()).build();
        return verifier.verify(token);
    }

    @Override
    public String getSubjectClaim(String token) {
        return verifier(token).getSubject();
    }

    @Override
    public String[] getRoles(String token) {
        return verifier(token).getClaim("roles").asArray(String.class);
    }

    @Override
    public RefreshTokenResponse refreshToken( RefreshToken requestRefreshToken ) throws IOException {
        try{ // verify the refresh token
            RefreshToken savedRefreshToken = refreshTokenService.getRefreshToken( requestRefreshToken.getId() );
            String refreshToken = savedRefreshToken.getRefreshToken();
            String username = getSubjectClaim( refreshToken );
            if(username != null && savedRefreshToken.getAccount().getUsername().equals( username )){
                RefreshTokenResponse response = new RefreshTokenResponse();
                response.setRefreshTokenId( savedRefreshToken.getId() );
                response.setAccessToken( accessToken( savedRefreshToken.getAccount() ) );
                return response;
            }
        }catch( Exception exception ){
            // if the refresh token is expired
            // remove the refresh token in the database
            refreshTokenService.removeRefreshToken( requestRefreshToken.getId() );
            logger.info("Error Logging in: {}", exception.getMessage());
            throw exception;
        }
        throw new NotFoundException("Refresh Token Not Found");
    }
}
