package com.project.inventory.store.product.wishlist.controller;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.service.ProductService;
import com.project.inventory.store.product.wishlist.model.Wishlist;
import com.project.inventory.store.product.wishlist.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping( value = "/api/v1/wishlist" )
public class WishlistController {

    @Autowired
    private WishlistService wishlistService;

    @Autowired
    private ProductService productService;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @RequestMapping( value = "", method = RequestMethod.POST )
    public ResponseEntity<?> saveWishlist(@RequestBody Product product) {
        Account account = authenticatedUser.getUserDetails();
        Product prod = productService.getProductById( product.getId() );
        Wishlist wishlist = new Wishlist();
        wishlist.setAccountId(account.getId());
        wishlist.setProduct(prod);
        wishlistService.saveWishlist( wishlist );
        return new ResponseEntity<>(wishlist, HttpStatus.OK );
    }

    @RequestMapping( value = "", method = RequestMethod.GET )
    public ResponseEntity<?> getWishlists( @RequestParam( value = "query", defaultValue = "" ) String query,
                                        @RequestParam( value = "page", defaultValue = "0" ) Integer page,
                                        @RequestParam( value = "limit", defaultValue = "10" ) Integer limit ) {
        Map<String, Object> response = new HashMap<>();

        Pageable pageable = PageRequest.of( page, limit );
        Account account = authenticatedUser.getUserDetails();

        Page<Wishlist> wishlist = wishlistService.getWishlist( account.getId(), query, pageable );
        response.put("data", wishlist.getContent());
        response.put("currentPage", wishlist.getNumber());
        response.put("totalItems", wishlist.getTotalElements());
        response.put("totalPages", wishlist.getTotalPages());

        return new ResponseEntity<>( response, HttpStatus.OK );
    }

    @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
    public ResponseEntity<?> deleteWishlist( @PathVariable int id ) {
        Map<String, Object> response = new HashMap<>();
        try {
            wishlistService.deleteWishlist( id );
        } catch ( Exception e ) {
            response.put("message", "Server Error. Please try again later.");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST );
        }
        return new ResponseEntity<>( HttpStatus.OK );
    }
}
