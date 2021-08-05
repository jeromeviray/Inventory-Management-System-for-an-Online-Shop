package com.project.inventory.jwtUtil.refreshToken.service.impl;

import com.project.inventory.common.persmision.model.Account;
import com.project.inventory.exception.ForbiddenException;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import com.project.inventory.jwtUtil.refreshToken.repository.RefreshTokenRepository;
import com.project.inventory.jwtUtil.refreshToken.service.RefreshTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    Logger logger = LoggerFactory.getLogger(RefreshTokenServiceImpl.class);
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken saveRefreshToken( String token, Account account ) {
      try{
          RefreshToken refreshToken = new RefreshToken();

          refreshToken.setRefreshToken( token );
          refreshToken.setAccount( account );

          return refreshTokenRepository.save( refreshToken );
      }catch (DataIntegrityViolationException e){
          logger.info("Error log in : {}", e.getMessage());
          throw e;
      }
    }

    @Override
    public RefreshToken getRefreshToken( String id ) {
        return refreshTokenRepository.findById( id ).orElseThrow(() -> new ForbiddenException("Refresh Token not Found.") );
    }

    @Override
    public void removeRefreshToken( String id ) {
        RefreshToken refreshToken = getRefreshToken( id );
        refreshTokenRepository.delete( refreshToken );
    }
}
