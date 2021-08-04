package com.project.inventory.webSecurity.oauth2.successHandler;

import com.project.inventory.jwtUtil.provider.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Autowired
    private JwtProvider jwtProvider;
    
}
