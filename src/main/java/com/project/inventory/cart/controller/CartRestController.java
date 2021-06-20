package com.project.inventory.cart.controller;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.cart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/cart")
public class CartRestController {

    @Autowired
    private CartService cartService;

    @RequestMapping(value = "/{accountId}", method = RequestMethod.GET)
    public ResponseEntity<Cart> getCart(@PathVariable int accountId){
        return new ResponseEntity(cartService.getCartByAccountIdDto(accountId), HttpStatus.ACCEPTED);
    }

    @RequestMapping(value = "/products/{cartId}", method = RequestMethod.GET)
    public ResponseEntity<CartItem> getCartProducts(@PathVariable int cartId){
        return new ResponseEntity(cartService.getCartProducts(cartId), HttpStatus.OK);
    }
}
