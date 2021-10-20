package com.project.inventory.store.cart.cartItem.controller;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.cartItem.model.CartItemDto;
import com.project.inventory.store.cart.cartItem.service.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping(value = "api/v1/cart/item")
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @RequestMapping(value = "/add/{productId}", method = RequestMethod.POST)
    public ResponseEntity<?> addToCart(@PathVariable int productId) throws ParseException {
        cartItemService.addCartItem(productId);
        return new ResponseEntity(HttpStatus.OK);
    }
    @RequestMapping(value = "/{action}/quantity/{productId}", method = RequestMethod.PUT)
    public ResponseEntity<?> increaseQuantity(@PathVariable int productId, @PathVariable String action) throws ParseException {
        CartItem cartItem;
        switch ( action ){
            case "increase":
                cartItem = cartItemService.increaseQuantity( productId );
                break;
            case "decrease":
                cartItem = cartItemService.decreaseQuantity( productId );
                break;
            default:
                throw new IllegalStateException( "Unexpected value: " + action );
        }
        return new ResponseEntity( HttpStatus.OK);
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
