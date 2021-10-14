package com.project.inventory.common.permission.verificationCode.repository;

import com.project.inventory.common.permission.verificationCode.model.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {

    Optional<VerificationCode> findByToken( String token);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM verification_codes WHERE id =:id", nativeQuery = true)
    void deleteVerificationCode( @Param( "id" ) int id);
}
