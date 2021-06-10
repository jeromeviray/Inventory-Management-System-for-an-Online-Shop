package com.project.inventory.cart.service.impl;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.cart.repository.CartRepository;
import com.project.inventory.cart.service.CartService;
import com.project.inventory.exception.account.AccountNotFoundException;
import com.project.inventory.exception.cart.CartNotFound;
import com.project.inventory.permission.model.Account;
import com.project.inventory.permission.repository.AccountRepository;
import com.project.inventory.permission.service.AccountService;
import com.project.inventory.product.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AccountService accountService;

    @Override
    public Cart getCartByCartIdAndAccountId(int cartId, int accountId) {
        logger.info("{}", cartId +" " +accountId);
        return cartRepository.findByIdAndAccountId(cartId, accountId);
    }

    @Override
    public Cart createCart(int accountId) {
        Account account = accountService.getAccountById(accountId);
        Cart cart = new Cart();

        if(account == null) throw new AccountNotFoundException("Account with ID '"+accountId+"' not Found");

        cart.setAccount(account);
        logger.info("{}", cart +" created successfully");

        return cartRepository.save(cart);

    }

    @Override
    public Cart getCartByAccountId(int accountId) {
        return cartRepository.findByAccountId(accountId);
    }

    @Override
    public Cart getCartById(int id) {
        Cart cart = cartRepository.findById(id);
        if(cart != null){
            return cart;
        }
        throw new CartNotFound("Cart not Found");
    }

    @Override
    public List<CartItem> getCartProducts(int cartId){
        List<CartItem> cartItems = new ArrayList<>();

        Cart cart = getCartById(cartId);

        for (CartItem cartItem : cart.getCartItems()){
            cartItems.add(cartItem);
        }

        return cartItems;
    }
}
