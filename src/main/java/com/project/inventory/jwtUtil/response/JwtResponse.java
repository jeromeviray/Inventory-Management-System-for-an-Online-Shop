package com.project.inventory.jwtUtil.response;

import java.util.List;
import java.util.Map;

public class JwtResponse {
    private final String type = "Bearer ";
    private String username;
    private String accessToken;
    private String refreshToken;
    private Map<String, String> roles;

    public JwtResponse () {
    }

    public JwtResponse ( String username, String accessToken, String refreshToken ) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public JwtResponse(String username, String accessToken, String refreshToken, Map<String, String> roles) {
        this.username = username;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.roles = roles;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername ( String username ) {
        this.username = username;
    }

    public String getAccessToken () {
        return accessToken;
    }

    public void setAccessToken ( String accessToken ) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken () {
        return refreshToken;
    }

    public void setRefreshToken ( String refreshToken ) {
        this.refreshToken = refreshToken;
    }

    public String getType () {
        return type;
    }



    public Map<String, String> getRoles() {
        return roles;
    }

    public void setRoles( Map<String, String> roles ) {
        this.roles = roles;
    }
    @Override
    public String toString() {
        return "JwtResponse{" +
                "type='" + type + '\'' +
                ", username='" + username + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", roles=" + roles +
                '}';
    }
}
