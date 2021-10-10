package com.project.inventory.common.user.repository;

import com.project.inventory.common.user.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findById( int id );

    @Query( value = "SELECT * FROM  users user " +
            "JOIN accounts account on user.account_id = account.id " +
            "where (account.is_not_banned = 1 AND account.is_not_deleted =1) AND account.id =:accountId", nativeQuery = true )
    Optional<User> findByAccountId( @Param( "accountId" ) int accountId );

    @Query( value = "select * from users user " +
            "join accounts account on user.account_id = account.id " +
            "left join user_privileges privileges on account.id = privileges.account_id " +
            "left join user_roles role on privileges.role_id = role.id " +
            "where account.is_not_banned = 1 " +
            "AND account.is_not_deleted = 1 " +
            "AND role.role_name LIKE concat('%',:role, '%') " +
            "AND (user.first_name LIKE concat('%',:query, '%') " +
            "OR user.last_name LIKE concat('%',:query, '%') " +
            "OR account.username LIKE concat('%',:query, '%'))", nativeQuery = true )
    Page<User> findAll( @Param( "query" ) String query, @Param( "role" ) String role, Pageable pageable );
}
