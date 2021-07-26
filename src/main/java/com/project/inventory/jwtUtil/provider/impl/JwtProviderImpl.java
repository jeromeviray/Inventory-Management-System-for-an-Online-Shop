package com.project.inventory.jwtUtil.provider.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class JwtProviderImpl implements JwtProvider {
    Logger logger = LoggerFactory.getLogger(JwtProviderImpl.class);

    private static final Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 1hr 3600000 or 10*60*1000 10 minutes duration

    private static final Date refreshTokenExpiresAt = new Date(System.currentTimeMillis() + 604800000);

    private String SECRET_KEY = "oqda#x!@jkd!@hda2";

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

    @Override
    public String accessToken(User user) {
        logger.info("{}", SECRET_KEY);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        String access = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(accessTokenExpiresAt)
                .withIssuedAt(new Date())
                .withClaim("roles", user.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);

        logger.info("{}", access);

        return access;

    }

    @Override
    public String refreshToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(refreshTokenExpiresAt)
                .withIssuedAt(new Date())
                .withClaim("roles", user.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }
}
