package com.project.inventory.webSecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.BeanUtils;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger( CustomAuthorizationFilter.class );
    @Autowired
    private JwtProvider jwtProvider = BeanUtils.getBean( JwtProvider.class );

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {

        if( request.getServletPath().equals( "/api/v1/account/login" )
                || request.getServletPath().equals( "/api/v1/account/token/refresh" )
                || request.getServletPath().equals("/oauth2/authorize/google")) {
            filterChain.doFilter( request, response );
        } else {
            String token = resolveToken( request );
            if( token != null ) {
                try {
                    String username = jwtProvider.getSubjectClaim( token );

                    String[] roles = jwtProvider.getRoles( token );
//                    Collection<SimpleGrantedAuthority> authorities = new ArrayList<> ();
//                    stream ( roles ).forEach ( role -> {
//                        authorities.add ( new SimpleGrantedAuthority ( role ) );
//                    } );
                    SecurityContextHolder.getContext().setAuthentication( getAuthentication( username, getGrantedAuthorities( roles ) ) );

                    filterChain.doFilter( request, response );
                } catch( Exception exception ) {
                    response.setStatus( FORBIDDEN.value() );
                    logger.info( "Error Logging in line 56: {}", exception.getMessage() );
                    Map<String, String> error = new HashMap<>();
                    error.put( "error_message", exception.getMessage() );
                    response.setContentType( APPLICATION_JSON_VALUE );
                    new ObjectMapper().writeValue( response.getOutputStream(), error );

                }
            } else {
                filterChain.doFilter( request, response );
            }
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities( String[] roles ) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for( String role : roles ) {
            authorities.add( new SimpleGrantedAuthority( "ROLE_" + role ) );
        }
        return authorities;
    }

    public Authentication getAuthentication( String username, List<GrantedAuthority> authorities ) {
        return new UsernamePasswordAuthenticationToken( username, null, authorities );
    }

    public String resolveToken( HttpServletRequest req ) {
        String header = req.getHeader( AUTHORIZATION );
        if( header != null && header.startsWith( "Bearer " ) ) {
            return header.substring( 7, header.length() );
        }
        return null;
    }
}
