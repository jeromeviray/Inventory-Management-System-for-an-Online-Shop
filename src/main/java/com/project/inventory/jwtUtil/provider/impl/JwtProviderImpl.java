package com.project.inventory.jwtUtil.provider.impl;


import com.project.inventory.BeanUtils;
import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.role.model.Role;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.common.permission.service.impl.AccountServiceImpl;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.jwtUtil.provider.JwtProvider;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshTokenResponse;
import com.project.inventory.jwtUtil.refreshToken.service.RefreshTokenService;
import com.project.inventory.jwtUtil.refreshToken.service.impl.RefreshTokenServiceImpl;
import com.project.inventory.webSecurity.impl.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtProviderImpl implements JwtProvider {
    Logger logger = LoggerFactory.getLogger( JwtProviderImpl.class );

    private static final Date accessTokenExpiresAt = new Date( System.currentTimeMillis() + (3600000 * 24) ); // 1hr 3600000 or 10*60*1000 10 minutes duration

    private static final Date refreshTokenExpiresAt = new Date( System.currentTimeMillis() + 604800000 );

    private String SECRET_KEY = Base64.getEncoder().encodeToString( "926D96C90030DD58429D2751AC1BDBBC".getBytes() );

    @Autowired
    private AccountService accountService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public String getUserNameFromToken( String token ) {
        return getClaimFromToken( token, Claims::getSubject );
    }

    @Override
    public Date getExpirationDateFromToken( String token ) {
        return getClaimFromToken( token, Claims::getExpiration );
    }

    @Override
    public <T> T getClaimFromToken( String token, Function<Claims, T> claimsResolver ) {
        final Claims claims = getAllClaimsFromToken( token );
        return claimsResolver.apply( claims );
    }

    @Override
    public Claims getAllClaimsFromToken( String token ) {
        return Jwts.parser().setSigningKey( SECRET_KEY ).parseClaimsJws( token ).getBody();
    }

    @Override
    public Boolean isTokenExpired( String token ) {
        final Date expiration = getExpirationDateFromToken( token );
        return expiration.before( new Date() );
    }

    @Override
    public String generateAccessToken( UserDetails userDetails ) {
        return doGenerateAccessToken( userDetails.getUsername() );
    }

    @Override
    public String generateRefreshToken( UserDetails userDetails ) {
        return doGenerateRefreshToken( userDetails.getUsername() );
    }

    @Override
    public String generateOAuth2AccessToken( String username ) {
        return doGenerateAccessToken( username );
    }

    @Override
    public String generateOAuth2RefreshToken( String username ) {
        return doGenerateRefreshToken( username );
    }

    @Override
    public String doGenerateAccessToken( String username ) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims( claims )
                .setSubject( username )
                .setIssuedAt( new Date( System.currentTimeMillis() ) )
                .setExpiration( accessTokenExpiresAt )
                .signWith( SignatureAlgorithm.HS512, SECRET_KEY ).compact();
    }

    @Override
    public String doGenerateRefreshToken( String username ) {
        Map<String, Object> claims = new HashMap<>();
        try {
            Account account = accountService.getAccountByUsername( username );
            //generate refres token
            String refreshToken = Jwts.builder()
                    .setClaims( claims )
                    .setSubject( username )
                    .setIssuedAt( new Date( System.currentTimeMillis() ) )
                    .setExpiration( new Date( System.currentTimeMillis() + 604800000 ) )
                    .signWith( SignatureAlgorithm.HS512, SECRET_KEY ).compact();
            // save and get the saved token
            Optional<RefreshToken> existingRefreshToken = refreshTokenService.getFreshTokenByAccountId( account.getId() );
            //filtering data
            if ( existingRefreshToken.isPresent() ) {
                return existingRefreshToken.get().getId();
            } else {
                return refreshTokenService.saveRefreshToken( refreshToken, account )
                        .getId();
            }
        } catch ( Exception e ) {
            throw new NotFoundException( e.getMessage() );
        }

    }

    @Override
    public Boolean validateToken( String token, String username ) {
        final String usernameFromToken = getUserNameFromToken( token );
        return ( usernameFromToken.equals( username ) && !isTokenExpired( token ) );
    }

    @Override
    public UserDetails getUserDetails( String username ) {
        UserDetails userDetails = userDetailsService.loadUserByUsername( username );
        return userDetails;
    }

    @Override
    public List<GrantedAuthority> getAuthorities( Account account ) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for ( Role role : account.getRoles() ) {
            authorities.add( new SimpleGrantedAuthority( "ROLE_" + role.getRoleName() ) );
        }

        return authorities;
    }

    @Override
    public RefreshTokenResponse validateAndGetAccessToken( RefreshToken refreshToken ) {
        try { // verify the refresh token
            RefreshToken savedRefreshToken = refreshTokenService.getRefreshToken( refreshToken.getId() );
            String token = savedRefreshToken.getRefreshToken();

            String username = getUserNameFromToken( token );

            if ( savedRefreshToken.getAccount().getUsername().equals( username ) ) {
                if ( validateToken( token, username ) ) {
                    RefreshTokenResponse response = new RefreshTokenResponse();
                    response.setRefreshTokenId( savedRefreshToken.getId() );
                    response.setAccessToken( doGenerateAccessToken( username ) );
                    return response;
                }
            }
        } catch ( Exception exception ) {
            // if the refresh token is expired
            // remove the refresh token in the database
            refreshTokenService.removeRefreshToken( refreshToken.getId() );
            logger.info( "Error Logging in: {}", exception.getMessage() );
            throw exception;
        }
        throw new NotFoundException( "Refresh Token Not Found" );
    }
}
