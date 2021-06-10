package com.project.inventory.cart.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.cart.service.CartService;
import com.project.inventory.jsonView.View;
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
    @JsonView(value = View.ProductView.CartView.class)
    public ResponseEntity<Cart> getCart(@PathVariable int accountId){
        return new ResponseEntity(cartService.getCartByAccountId(accountId), HttpStatus.ACCEPTED);
    }
}