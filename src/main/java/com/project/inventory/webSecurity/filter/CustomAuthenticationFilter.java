package com.project.inventory.webSecurity.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.service.AuthenticatedUser;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.provider.impl.JwtProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter  {
    Logger logger = LoggerFactory.getLogger(CustomAuthenticationFilter.class);

    private JwtProviderImpl jwtProviderImpl;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    private AuthenticationManager authenticationManager;

    private static final Date accessTokenExpiresAt = new Date(System.currentTimeMillis() + 10 * 60 * 1000); // 1hr 3600000 or 10*60*1000 10 minutes duration

    private static final Date refreshTokenExpiresAt = new Date(System.currentTimeMillis() + 604800000);
    private String SECRET_KEY = "oqda#x!@jkd!@hda2";

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
    }

//    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//        this.authenticationManager = authenticationManager;
//    }

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/user/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Account account = new ObjectMapper().readValue(request.getInputStream(), Account.class);
            logger.info("Filtering this user with username: {}", account.getPassword());
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(account.getUsername(), account.getPassword());
            return authenticationManager.authenticate(authenticationToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        logger.info("succeff");
        User user = (User) authentication.getPrincipal();
//        String accessToken = jwtProviderImpl.accessToken(user);
//        String refreshToken = jwtProviderImpl.refreshToken(user);
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);

        String access = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(accessTokenExpiresAt)
                .withIssuedAt(new Date())
                .withClaim("roles", user.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);

        String refresh = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(refreshTokenExpiresAt)
                .withIssuedAt(new Date())
                .withClaim("roles", user.getAuthorities()
                        .stream().map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
        response.setHeader("access_token", access);
        response.setHeader("refresh_token", refresh);
    }
}
