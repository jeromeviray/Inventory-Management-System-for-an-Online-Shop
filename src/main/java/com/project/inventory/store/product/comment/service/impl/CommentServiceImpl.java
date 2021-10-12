package com.project.inventory.store.product.comment.service.impl;

import com.project.inventory.store.product.comment.model.Comment;
import com.project.inventory.store.product.comment.repository.CommentRepository;
import com.project.inventory.store.product.comment.service.CommentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    Logger logger = LoggerFactory.getLogger( CommentServiceImpl.class );

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public void saveComment( Comment comment ) {
        commentRepository.save( comment );
    }

    @Override
    public Page<Comment> getComments( Integer productId, Pageable pageable ) {
        return commentRepository.getComments( productId, pageable);
    }

    @Override
    public Comment getCommentByProductId( Integer accountId, Integer productId ) {
        return commentRepository.findCommentByProductId(accountId, productId);
    }

    @Override
    public void deleteComment( int id ) {
        commentRepository.deleteById( id );
    }

    @Override
    public Integer getProductRating( Integer productId ) {
        return commentRepository.getProductRating( productId );
    }
}
