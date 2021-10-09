package com.project.inventory.store.product.wishlist.service;

import com.project.inventory.store.product.wishlist.model.Wishlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface WishlistService {
    void saveWishlist( Wishlist Wishlist );

    Page<Wishlist> getWishlist( Integer accountId, String query, Pageable pageable );

    Wishlist getWishlistByProductId( Integer accountId, Integer productId );

    void deleteWishlist( int id );

}
