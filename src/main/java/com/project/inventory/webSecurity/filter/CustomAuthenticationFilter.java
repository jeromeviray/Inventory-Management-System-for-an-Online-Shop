package com.project.inventory.webSecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.BeanUtils;
import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.refreshToken.service.RefreshTokenService;
import com.project.inventory.jwtUtil.response.JwtResponse;
import com.project.inventory.webSecurity.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    Logger logger = LoggerFactory.getLogger( CustomAuthenticationFilter.class );

    @Autowired
    private AccountService accountService = BeanUtils.getBean( AccountService.class );
    @Autowired
    private JwtProvider jwtProvider = BeanUtils.getBean( JwtProvider.class );
    @Autowired
    private RefreshTokenService refreshTokenService = BeanUtils.getBean( RefreshTokenService.class );
    @Autowired
    private UserDetailsServiceImpl userDetailsService = BeanUtils.getBean( UserDetailsServiceImpl.class );

    private AuthenticationManager authenticationManager;

    public CustomAuthenticationFilter() {
    }

    public CustomAuthenticationFilter( AuthenticationManager authenticationManager ) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl( "/api/v1/account/login" );
    }

    @Override
    public Authentication attemptAuthentication( HttpServletRequest request, HttpServletResponse response ) throws AuthenticationException {
        try {
            Account account = new ObjectMapper().readValue( request.getInputStream(), Account.class );
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken( account.getUsername(), account.getPassword() );
            return authenticationManager.authenticate( authenticationToken );
        } catch ( IOException e ) {
            logger.info( "Error log in : {}", e.getMessage() );
            throw new BadCredentialsException( e.getMessage() );
        } catch ( LockedException e ) {
            logger.info( "Error log in : {}", e.getMessage() );
            throw e;
        } catch ( DisabledException e ) {
            logger.info( "Error log in : {}", e.getMessage() );
            throw e;
        } catch ( BadCredentialsException e ) {
            logger.info( "Error log in : {}", e.getMessage() );
            throw new BadCredentialsException( e.getMessage() );
        } catch ( AccountExpiredException e ) {
            logger.info( "Error log in : {}", e.getMessage() );
            throw e;
        }
    }

    @Override
    protected void successfulAuthentication( HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication ) throws IOException, ServletException {
        User user = ( User ) authentication.getPrincipal();
        Account account = accountService.getAccountByUsername( user.getUsername() );
        response.setContentType( APPLICATION_JSON_VALUE );
        UserDetails userDetails = userDetailsService.loadUserByUsername( user.getUsername() );
        // tokens
        String accessToken = jwtProvider.generateAccessToken( userDetails );
        String refreshToken = jwtProvider.generateRefreshToken( userDetails );
        Map<String, String> roles = new HashMap<>();
        for ( Role role : account.getRoles() ) {
            roles.put( "roleName", role.getRoleName() );
        }

        JwtResponse jwtResponse = new JwtResponse( user.getUsername(), accessToken, refreshToken, roles );
        new ObjectMapper().writeValue( response.getOutputStream(), jwtResponse );
    }

}
