package com.project.inventory.cart.cartItem.controller;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.cartItem.service.CartItemService;
import com.project.inventory.cart.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Entity;

@RestController
@RequestMapping(value = "api/v1/cart/item")
public class CartItemRestController {

    @Autowired
    private CartItemService cartItemService;
    private int accountId = 2;

    @RequestMapping(value = "/add/{productId}", method = RequestMethod.POST)
    public ResponseEntity<CartItem> addToCart(@PathVariable int productId, @RequestBody CartItem cartItem){
        cartItemService.addCartItem(productId, cartItem);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
    @RequestMapping(value = "/increase/quantity/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Integer> increaseQuantity(@PathVariable int productId){
        return new ResponseEntity(cartItemService.increaseQuantity(accountId, productId), HttpStatus.OK);
    }
    @RequestMapping(value = "/decrease/quantity/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Integer> decreaseQuantity(@PathVariable int productId){
        return new ResponseEntity(cartItemService.decreaseQuantity(accountId, productId), HttpStatus.OK);
    }
}
