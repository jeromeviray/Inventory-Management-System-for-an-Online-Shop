package com.project.inventory.webSecurity.oauth2.successHandler;

import com.project.inventory.BeanUtils;
import com.project.inventory.common.persmision.role.model.Role;
import com.project.inventory.common.persmision.service.AccountService;
import com.project.inventory.exception.BadRequestException;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.webSecurity.config.AppProperties;
import com.project.inventory.webSecurity.impl.UserDetailsServiceImpl;
import com.project.inventory.webSecurity.oauth2.cookie.HttpCookieOAuth2RequestRepository;
import com.project.inventory.webSecurity.oauth2.cookie.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.project.inventory.webSecurity.oauth2.cookie.HttpCookieOAuth2RequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger( OAuth2AuthenticationSuccessHandler.class );

    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private HttpCookieOAuth2RequestRepository httpCookieOAuth2RequestRepository;
    @Autowired
    private AppProperties appProperties;
    @Autowired
    private AccountService accountService;

    @Override
    public void onAuthenticationSuccess( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) throws IOException, ServletException {
        String url = determineTargetUrl( request, response, authentication );
        if ( response.isCommitted() ) {
            logger.debug( "Response has already been committed. Unable to redirect to " + url );
            return;
        }
        clearAuthenticationAttributes( request, response );
        getRedirectStrategy().sendRedirect( request, response, url );
    }

    @Override
    protected String determineTargetUrl( HttpServletRequest request, HttpServletResponse response, Authentication authentication ) {
        Optional<String> redirectUri = CookieUtils.getCookie( request, REDIRECT_URI_PARAM_COOKIE_NAME )
                .map( Cookie::getValue );
        if ( redirectUri.isPresent() && !isAuthorizedRedirectUri( redirectUri.get() ) ) {
            throw new BadRequestException( "Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication" );
        }

        String targetUrl = redirectUri.orElse( getDefaultTargetUrl() );
        String username = authentication.getName().substring( 0, authentication.getName().indexOf( "@" ) );

        String accessToken = jwtProvider.generateOAuth2AccessToken( username );
        String refreshToken = jwtProvider.generateOAuth2RefreshToken( username );
        String roles = getRoles( accountService.getAccountByUsername( username ).getRoles() );

        String queryParams = String.format(
                "username=%s&accessToken=%s&refreshToken=%s&roles=%s",
                username,
                accessToken,
                refreshToken,
                roles
        );
        return UriComponentsBuilder.fromUriString( targetUrl )
                .queryParam( queryParams )
                .build().toUriString();

    }

    protected void clearAuthenticationAttributes( HttpServletRequest request, HttpServletResponse response ) {
        super.clearAuthenticationAttributes( request );
        httpCookieOAuth2RequestRepository.removeAuthorizationRequest( request, response );
    }

    private String getRoles( Set<Role> roles ) {
        List<String> getRolesName = new ArrayList<>();
        for ( Role role : roles ) {
            getRolesName.add( role.getRoleName() );
        }
        return getRolesName.get( 0 );
    }

    private Boolean isAuthorizedRedirectUri( String uri ) {
        URI redirectUri = URI.create( uri );
        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch( authorizedRedirectUri -> {
                    URI authorizedURI = URI.create( authorizedRedirectUri );
                    if ( authorizedURI.getHost().equals( redirectUri.getHost() ) && authorizedURI.getPort() == redirectUri.getPort() ) {
                        return true;
                    }
                    return false;
                } );
    }
}
