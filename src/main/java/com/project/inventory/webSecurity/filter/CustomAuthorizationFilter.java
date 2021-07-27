package com.project.inventory.webSecurity.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.provider.impl.JwtProviderImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class CustomAuthorizationFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(CustomAuthorizationFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        JwtProvider jwtProvider = new JwtProviderImpl();

        if(request.getServletPath().equals("/api/v1/account/login")){
            filterChain.doFilter(request, response);
        }else {
            try{
                String token = resolveToken(request);
                String username = jwtProvider.getSubjectClaim(token);
                logger.info("Do Filter this Account {}", username);
                String[] roles = jwtProvider.getRoles(token);
                Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
                stream(roles).forEach(role -> {
                    authorities.add(new SimpleGrantedAuthority(role));
                });
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(request, response);
            }catch (Exception exception){
                logger.info("Error Logging in: {}", exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), exception.getCause());
            }

        }
    }
    private Authentication getAuthentication(UserDetails userDetails){
        return new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities() );
    }
    private String resolveToken(HttpServletRequest req) {
        String header = req.getHeader(AUTHORIZATION);
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7, header.length());
        }
        return null;
    }
}
