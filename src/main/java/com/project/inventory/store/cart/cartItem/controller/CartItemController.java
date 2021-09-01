package com.project.inventory.store.cart.cartItem.controller;

import com.project.inventory.store.cart.cartItem.model.CartItemDto;
import com.project.inventory.store.cart.cartItem.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "api/v1/cart/item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @RequestMapping(value = "/add/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> addToCart(@PathVariable int productId){
        cartItemService.addCartItem(productId);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/increase/quantity/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Integer> increaseQuantity(@PathVariable int productId){
        return new ResponseEntity(cartItemService.increaseQuantity(productId), HttpStatus.OK);
    }
    @RequestMapping(value = "/decrease/quantity/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Integer> decreaseQuantity(@PathVariable int productId){
        return new ResponseEntity(cartItemService.decreaseQuantity(productId), HttpStatus.OK);
    }

    @RequestMapping(value = "/remove/{cartItemId}", method = RequestMethod.DELETE)
    public ResponseEntity removeItem(@PathVariable int cartItemId){
        cartItemService.removeItem(cartItemId);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public ResponseEntity<CartItemDto> getCartItem(@PathVariable int id){

        return new ResponseEntity( cartItemService.getCartItem(id), HttpStatus.OK);
    }
}