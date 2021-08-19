package com.project.inventory.webSecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.BeanUtils;
import com.project.inventory.exception.apiError.ApiError;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.webSecurity.impl.UserDetailsServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger( CustomAuthorizationFilter.class );
    @Autowired
    private JwtProvider jwtProvider = BeanUtils.getBean( JwtProvider.class );
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal( HttpServletRequest request, HttpServletResponse response, FilterChain filterChain ) throws ServletException, IOException {

        if( request.getServletPath().equals( "/api/v1/account/login" )
                || request.getServletPath().equals( "/api/v1/account/token/refresh" )
                || request.getServletPath().equals("/oauth2/authorize/google")) {
            filterChain.doFilter( request, response );
        } else {
            String token = resolveToken( request );
            String username = null;

            if( token != null ) {
                try {
                    username = jwtProvider.getUserNameFromToken( token );
                    if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                        UserDetails userDetails = jwtProvider.getUserDetails( username );

                        if(token != null && jwtProvider.validateToken( token, userDetails.getUsername() )){
                            SecurityContextHolder.getContext()
                                    .setAuthentication(new UsernamePasswordAuthenticationToken( userDetails.getUsername(), null, userDetails.getAuthorities() ));
                        }
                    }
                    filterChain.doFilter( request, response );
                } catch( Exception exception ) {
                    response.setStatus( FORBIDDEN.value() );
                    logger.info( "Error Logging in line 56: {}", exception.getMessage() );

                    response.setContentType( APPLICATION_JSON_VALUE );
                    ApiError error = new ApiError(new Date(),
                            HttpStatus.FORBIDDEN.value(),
                            FORBIDDEN,
                            exception.getMessage(),
                            request.getServletPath());
                    new ObjectMapper().writeValue( response.getOutputStream(), error );

                }
            } else {
                filterChain.doFilter( request, response );
            }
        }
    }

//    private List<GrantedAuthority> getGrantedAuthorities(List<GrantedAuthority> authorities) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        for( String role : roles ) {
//            authorities.add( new SimpleGrantedAuthority( "ROLE_" + role ) );
//        }
//        return authorities;
//    }

//    public Authentication getAuthentication( String username, List<GrantedAuthority> authorities ) {
//        return new UsernamePasswordAuthenticationToken( username, null, authorities );
//    }

    public String resolveToken( HttpServletRequest req ) {
        String header = req.getHeader( AUTHORIZATION );
        if( header != null && header.startsWith( "Bearer " ) ) {
            return header.substring( 7, header.length() );
        }
        return null;
    }
}
