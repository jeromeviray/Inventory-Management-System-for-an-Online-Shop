package com.project.inventory.webSecurity.oauth2.service;

import com.project.inventory.webSecurity.oauth2.AuthProvider;
import com.project.inventory.webSecurity.oauth2.service.provider.GoogleOAuth2UserInfo;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase( AuthProvider.google.toString())) {
            return new GoogleOAuth2UserInfo( attributes );
        }
        else {
            throw new OAuth2AuthenticationException("Sorry! Login with " + registrationId + " is not supported yet.");
        }
//        } else if (registrationId.equalsIgnoreCase(AuthProvider.FACEBOOK.toString())) {
//            return new FacebookOAuth2UserInfo(attributes);

    }
}
