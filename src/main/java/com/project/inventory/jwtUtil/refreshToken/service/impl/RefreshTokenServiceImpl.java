package com.project.inventory.jwtUtil.refreshToken.service.impl;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.exception.NotFoundException;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import com.project.inventory.jwtUtil.refreshToken.repository.RefreshTokenRepository;
import com.project.inventory.jwtUtil.refreshToken.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken saveRefreshToken( String token, Account account ) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setRefreshToken( token );
        refreshToken.setAccount( account );

        return refreshTokenRepository.save( refreshToken );
    }

    @Override
    public RefreshToken getRefreshToken( String id ) {
        return refreshTokenRepository.findById( id ).orElseThrow(() -> new NotFoundException("Refresh Token not Found.") );
    }

    @Override
    public void removeRefreshToken( String id ) {
        RefreshToken refreshToken = getRefreshToken( id );
        refreshTokenRepository.delete( refreshToken );
    }
}
