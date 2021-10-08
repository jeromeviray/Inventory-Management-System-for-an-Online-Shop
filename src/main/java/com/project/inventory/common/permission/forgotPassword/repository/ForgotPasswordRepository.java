package com.project.inventory.common.permission.forgotPassword.repository;

import com.project.inventory.common.permission.forgotPassword.model.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ForgotPasswordRepository extends JpaRepository<ForgotPassword, Integer> {

    Optional<ForgotPassword> findByToken( String token);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM account_forgot_password WHERE id =:id", nativeQuery = true)
    void deleteForgotPassword(@Param( "id" ) int id);
}