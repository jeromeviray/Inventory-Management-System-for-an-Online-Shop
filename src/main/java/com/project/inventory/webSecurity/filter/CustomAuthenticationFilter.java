package com.project.inventory.webSecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.BeanUtils;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.response.JwtResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    Logger logger = LoggerFactory.getLogger ( CustomAuthenticationFilter.class );

    @Autowired
    private AccountService accountService = BeanUtils.getBean ( AccountService.class );
    @Autowired
    private JwtProvider jwtProvider = BeanUtils.getBean ( JwtProvider.class );

    private AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter () {
    }

    public CustomAuthenticationFilter ( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl ( "/api/v1/account/login" );
    }

    @Override
    public Authentication attemptAuthentication ( HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException {
        try {
            Account account = new ObjectMapper ().readValue ( request.getInputStream (), Account.class );
            logger.info ( "Filtering user with username: {}", account.getUsername () );
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken ( account.getUsername (), account.getPassword () );
            return authenticationManager.authenticate ( authenticationToken );
        } catch ( IOException e ) {
            throw new RuntimeException ( e );
        }
    }

    @Override
    protected void successfulAuthentication ( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication ) throws IOException, ServletException {
        User user = ( User ) authentication.getPrincipal ();
        Account account = accountService.getAccountByUsername ( user.getUsername () );
        // tokens
        String access_token = jwtProvider.accessToken (account);
        String refresh_token = jwtProvider.refreshToken (account);

        JwtResponse jwtResponse = new JwtResponse (user.getUsername (), access_token, refresh_token);
        new ObjectMapper ().writeValue ( response.getOutputStream (), jwtResponse );
    }
}
