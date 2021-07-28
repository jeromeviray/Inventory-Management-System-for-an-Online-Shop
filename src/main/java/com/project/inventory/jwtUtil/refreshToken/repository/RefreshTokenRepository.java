package com.project.inventory.jwtUtil.refreshToken.repository;

import com.project.inventory.jwtUtil.refreshToken.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {

    Optional<RefreshToken> findById( String id );
}
