//package com.project.inventory.webSecurity.oauth2.cookie;
//
//import com.project.inventory.webSecurity.oauth2.cookie.utils.CookieUtils;
//import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
//import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//public class HttpCookieOAuth2RequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
//    public static final String REQUEST_COOKIE_NAME = "oauth2_auth_request";
//    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
//    private static final int cookieExpireSeconds = 180;
//
//    @Override
//    public OAuth2AuthorizationRequest loadAuthorizationRequest( HttpServletRequest httpServletRequest ) {
//        return CookieUtils.getCookie( httpServletRequest, REQUEST_COOKIE_NAME )
//                .map( (cookie) -> CookieUtils.deserialize( cookie, OAuth2AuthorizationRequest.class ) )
//                .orElseThrow();
//    }
//
//    @Override
//    public void saveAuthorizationRequest( OAuth2AuthorizationRequest oAuth2AuthorizationRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse ) {
//
//    }
//
//    @Override
//    public OAuth2AuthorizationRequest removeAuthorizationRequest( HttpServletRequest httpServletRequest ) {
//        return loadAuthorizationRequest( httpServletRequest );
//    }
//
//    @Override
//    public OAuth2AuthorizationRequest removeAuthorizationRequest( HttpServletRequest request, HttpServletResponse response ) {
//        return AuthorizationRequestRepository.super.removeAuthorizationRequest( request, response );
//    }
//}
