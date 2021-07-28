package com.project.inventory.jwtUtil.refreshToken.model;

public class RefreshTokenResponse {
    private String refreshTokenId;
    private String accessToken;
    private String type = "Bearer ";

    public String getRefreshTokenId() {
        return refreshTokenId;
    }

    public void setRefreshTokenId( String refreshTokenId ) {
        this.refreshTokenId = refreshTokenId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken( String accessToken ) {
        this.accessToken = accessToken;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "RefreshTokenResponse{" +
                "refreshTokenId='" + refreshTokenId + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
