package com.project.inventory.store.cart.controller;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.model.Cart;
import com.project.inventory.store.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;
    @Autowired
    private AuthenticatedUser authenticatedUser;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<Cart> getCart(){
        Account account = authenticatedUser.getUserDetails();
        return new ResponseEntity(cartService.getCartByAccountIdDto(account.getId()), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/products/{cartId}", method = RequestMethod.GET)
    public ResponseEntity<CartItem> getCartProducts(@PathVariable int cartId){
        return new ResponseEntity(cartService.getCartProducts(cartId), HttpStatus.OK);
    }
}
