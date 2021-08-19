package com.project.inventory.jwtUtil.provider;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshTokenResponse;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.List;
import java.util.function.Function;

public interface JwtProvider {

    String getUserNameFromToken( String token );

    Date getExpirationDateFromToken( String token );

    <T> T getClaimFromToken( String token, Function<Claims, T> claimsResolver );

    Claims getAllClaimsFromToken( String token );

    Boolean isTokenExpired( String token );

    String doGenerateAccessToken( String username );

    String doGenerateRefreshToken( String username );

    Boolean validateToken( String token, String username );

    UserDetails getUserDetails( String username );

    List<GrantedAuthority> getAuthorities( Account account );

    RefreshTokenResponse validateAndGetAccessToken( RefreshToken refreshToken );

    String generateAccessToken( UserDetails userDetails );

    String generateRefreshToken( UserDetails userDetails );

    String generateOAuth2AccessToken( String username );

    String generateOAuth2RefreshToken( String username );
}
