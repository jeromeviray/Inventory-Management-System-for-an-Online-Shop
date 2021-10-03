package com.project.inventory.common.user.repository;

import com.project.inventory.common.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById(int id);
    Optional<User> findByAccountId(int accountId);

    @Query(value = "SELECT * FROM users WHERE first_name LIKE concat('%',:query, '%') OR last_name LIKE concat('%',:query, '%')", nativeQuery = true)
    Page<User> findAll( @Param ( "query" ) String query, Pageable pageable );
}
