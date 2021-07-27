package com.project.inventory.jwtUtil.provider.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.response.JwtResponse;
import com.project.inventory.webSecurity.filter.CustomAuthenticationFilter;
import com.project.inventory.webSecurity.filter.CustomAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Service
public class JwtProviderImpl implements JwtProvider {
    Logger logger = LoggerFactory.getLogger(JwtProviderImpl.class);

    private static final Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 1hr 3600000 or 10*60*1000 10 minutes duration

    private static final Date refreshTokenExpiresAt = new Date(System.currentTimeMillis() + 604800000);

    private String SECRET_KEY = Base64.getEncoder().encodeToString("osqda#x!@jkd!@hda2".getBytes());

    @Autowired
    private AccountService accountService;
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
                        .stream().map(Role::getRoleName)
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
    public void verifierRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CustomAuthorizationFilter customAuthorizationFilter = new CustomAuthorizationFilter();
        String refreshToken = customAuthorizationFilter.resolveToken(request);
        if(refreshToken != null){
            try{
                String username = getSubjectClaim(refreshToken);
                logger.info("Get refresh token", username);
                Account account = accountService.getAccountByUsername(username);
                String accessToken = accessToken(account);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), new JwtResponse (username, accessToken, refreshToken) );

            }catch (Exception exception){
                logger.info("Error Logging in: {}", exception.getMessage());

                response.setStatus(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        }

    }
}
