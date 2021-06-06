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

    @RequestMapping(value = "/add/{productId}", method = RequestMethod.POST)
    public ResponseEntity<CartItem> addToCart(@PathVariable int productId, @RequestBody CartItem cartItem){
        cartItemService.addCartItem(productId, cartItem);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }
}
