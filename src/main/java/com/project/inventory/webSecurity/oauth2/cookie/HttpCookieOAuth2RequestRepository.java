package com.project.inventory.webSecurity.oauth2.cookie;

import com.nimbusds.oauth2.sdk.util.StringUtils;
import com.project.inventory.webSecurity.oauth2.cookie.utils.CookieUtils;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpCookieOAuth2RequestRepository implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {
    public static final String REQUEST_COOKIE_NAME = "oauth2_auth_request";
    public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirect_uri";
    private static final int cookieExpireSeconds = 180;

    @Override
    public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
        return CookieUtils.getCookie(request, REQUEST_COOKIE_NAME)
                .map((cookie) -> CookieUtils.deserialize(cookie, OAuth2AuthorizationRequest.class))
                .orElseThrow();
    }

    @Override
    public void saveAuthorizationRequest(OAuth2AuthorizationRequest oAuth2AuthorizationRequest, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if(oAuth2AuthorizationRequest == null){
            CookieUtils.deleteCookie(httpServletRequest, httpServletResponse, REQUEST_COOKIE_NAME);
            CookieUtils.deleteCookie(httpServletRequest, httpServletResponse, REDIRECT_URI_PARAM_COOKIE_NAME);
            return;
        }
        CookieUtils.addCookie(httpServletResponse, REQUEST_COOKIE_NAME, CookieUtils.serialize(oAuth2AuthorizationRequest), cookieExpireSeconds);
        String redirectUriAfterLogin = httpServletRequest.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
        if(StringUtils.isNotBlank(redirectUriAfterLogin)){
            CookieUtils.addCookie(httpServletResponse, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, cookieExpireSeconds);
        }
    }

    public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
        CookieUtils.deleteCookie(request, response, REQUEST_COOKIE_NAME);
        CookieUtils.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
    }

    @Override
    public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest httpServletRequest) {
        return loadAuthorizationRequest(httpServletRequest);
    }
}
