package com.project.inventory.webSecurity.oauth2.failureHandler;

import com.project.inventory.webSecurity.oauth2.cookie.HttpCookieOAuth2RequestRepository;
import com.project.inventory.webSecurity.oauth2.cookie.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.project.inventory.webSecurity.oauth2.cookie.HttpCookieOAuth2RequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    Logger logger = LoggerFactory.getLogger( OAuth2AuthenticationFailureHandler.class );
    @Autowired
    private HttpCookieOAuth2RequestRepository cookieOAuth2RequestRepository;

    @Override
    public void onAuthenticationFailure( HttpServletRequest request, HttpServletResponse response, AuthenticationException exception ) throws IOException, ServletException {
        String targetUrl  = CookieUtils.getCookie( request, REDIRECT_URI_PARAM_COOKIE_NAME )
                .map( Cookie::getValue )
                .orElse( ("/") );
        String queryParams = String.format(
                "error=%s", exception.getLocalizedMessage()
        );
        logger.info( "error" );
        targetUrl  = UriComponentsBuilder.fromUriString( targetUrl  )
                .queryParam( queryParams )
                .build()
                .toUriString();
        cookieOAuth2RequestRepository.removeAuthorizationRequest( request, response );

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}
