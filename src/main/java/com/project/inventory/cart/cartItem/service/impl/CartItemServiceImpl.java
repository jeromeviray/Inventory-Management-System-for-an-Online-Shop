package com.project.inventory.cart.cartItem.service.impl;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.cartItem.repository.CartItemRepository;
import com.project.inventory.cart.cartItem.service.CartItemService;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.cart.service.CartService;
import com.project.inventory.exception.product.ProductNotFound;
import com.project.inventory.product.model.Product;
import com.project.inventory.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayDataSource;
import org.springframework.stereotype.Service;

@Service
public class CartItemServiceImpl implements CartItemService {
    Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;

    @Override
    public void addCartItem(int productId, CartItem cartItem) {
        // get the product
        // get the cart that exist with account id
        Product product = productService.getProductById(productId);
        Cart cart = getCart(1);

        if(product == null) {
            throw new ProductNotFound("Product not Found");
        }else if(cart == null){
            // will create new cart
            // save the cart item

            cartItem.setAmount(product.getPrice());
            cartItem.setProduct(product);

            Cart savedCart = cartService.createCart(1);
            cartItem.setCart(savedCart);

            cartItemRepository.save(cartItem);
            logger.info("{}", cartItem.getId());

        }


        for(CartItem savedCartItem : cart.getCartItems()){

            if(savedCartItem.getProduct().getId() == productId){
//                incrementQuantity(savedCartItem.getId());
            }
        }
        // save the item if don't exist in cart item

//        logger.info("product id: "+product.getId());
//        cartItem.setAmount(product.getPrice());
//        cartItem.setProduct(product);
//        cartItem.setCart(cart);
//        cartItemRepository.save(cartItem);

    }
    public Cart getCart(int accountId){
        Cart cart = cartService.getCartByAccountId(accountId);
        if(cart != null){
            return cart;
        }
        return null;
    }
    public void incrementQuantity(int cartItemId){
        CartItem savedCartItem = cartItemRepository.findById(cartItemId);
        savedCartItem.setQuantity(savedCartItem.getQuantity() + 1); // increment the quantity of existing item in cart item

        cartItemRepository.save(savedCartItem);
    }
}
