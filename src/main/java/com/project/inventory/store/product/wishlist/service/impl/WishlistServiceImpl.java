package com.project.inventory.store.product.wishlist.service.impl;

import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.store.product.wishlist.model.Wishlist;
import com.project.inventory.store.product.wishlist.repository.WishlistRepository;
import com.project.inventory.store.product.wishlist.service.WishlistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WishlistServiceImpl implements WishlistService {
    Logger logger = LoggerFactory.getLogger( WishlistServiceImpl.class );

    @Autowired
    private WishlistRepository wishlistRepository;


    @Override
    public void saveWishlist( Wishlist wishlist ) {
        wishlistRepository.save( wishlist );
    }

    @Override
    public Page<Wishlist> getWishlist( Integer accountId, String query, Pageable pageable ) {
        return wishlistRepository.findWishlist(accountId, query, pageable);
    }

    @Override
    public Wishlist getWishlistByProductId( Integer accountId, Integer productId ) {
        return wishlistRepository.findWishlistByProductId(accountId, productId);
    }

    @Override
    public void deleteWishlist( int id ) {
        wishlistRepository.deleteById( id );
    }
}
