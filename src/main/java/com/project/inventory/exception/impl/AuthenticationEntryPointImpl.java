package com.project.inventory.exception.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.inventory.exception.apiError.ApiError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint, Serializable {
    private static final Logger logger = LoggerFactory.getLogger( AuthenticationEntryPointImpl.class );
    private static final long serialVersionUID = -8970718410437077606L;

    @Override
    public void commence( HttpServletRequest request, HttpServletResponse response, AuthenticationException e ) throws IOException, ServletException {
        logger.info( "{}", e.getMessage() );
        response.setStatus( HttpStatus.UNAUTHORIZED.value() );
        response.setContentType( APPLICATION_JSON_VALUE );

        ApiError error = new ApiError( new Date(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED,
                e.getMessage(),
                request.getServletPath() );

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write( mapper.writeValueAsString( error ) );
    }
}
