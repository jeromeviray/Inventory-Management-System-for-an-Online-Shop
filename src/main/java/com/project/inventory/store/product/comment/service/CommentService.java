package com.project.inventory.store.product.comment.service;

import com.project.inventory.store.product.comment.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    void saveComment( Comment Comment );

    Page<Comment> getComments( Integer productId, Pageable pageable );

    Comment getCommentByProductId( Integer accountId, Integer productId );

    void deleteComment( int id );

}
