package com.project.inventory.store.product.comment.repository;

import com.project.inventory.store.product.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query( value = "SELECT * FROM product_comments as w WHERE product_id = :productId ",
            nativeQuery = true )
    Page<Comment> getComments( Integer productId, Pageable pageable);

    @Query(value = "SELECT w.* " +
            "FROM product_comments as w " +
            "JOIN products as product ON w.product_id = product.id " +
            "WHERE product.id = :productId AND account_id = :accountId AND product.product_is_deleted = 0 ",
            nativeQuery = true)
    Comment findCommentByProductId( @Param( "accountId" ) Integer account_id, @Param("productId") Integer productId);
}
