package com.project.inventory.jwtUtil.provider;

import org.springframework.security.core.userdetails.User;

public interface JwtProvider {
    String accessToken(User user);
    String refreshToken(User user);
}
