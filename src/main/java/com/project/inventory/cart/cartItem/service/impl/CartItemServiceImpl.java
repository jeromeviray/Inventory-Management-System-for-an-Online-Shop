package com.project.inventory.cart.cartItem.service.impl;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.cartItem.repository.CartItemRepository;
import com.project.inventory.cart.cartItem.service.CartItemService;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.cart.service.CartService;
import com.project.inventory.exception.cartItem.CartItemNotValidException;
import com.project.inventory.product.model.Product;
import com.project.inventory.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
        int accountId = 2;
        Product product = productService.getAvailableProductById(productId);
        Cart cart = getCart(accountId);

        if(cart == null){
            // will create new cart
            // save the cart item
            createCart(cartItem, product, accountId);
        }else {
            // get the saved item
            CartItem savedCartItem = getCartItemByCartIdAndProductId(cart.getId(), product.getId());
            if(savedCartItem != null){

                logger.info("{}", savedCartItem.getId());

                int quantity = incrementQuantity(savedCartItem.getId());
                logger.info("Updated Quantity", quantity);
            }else {
                cartItem.setProduct(product);
                cartItem.setAmount(product.getPrice());
                cartItem.setQuantity(1);
                cartItem.setCart(cart);
                cartItemRepository.save(cartItem);
            }
        }
    }


    // item can be increment and decrement of the quantity
    // this is for api when the user is in shopping cart
    @Override
    public int increaseQuantity(int accountId, int productId) {
        Cart cart = cartService.getCartByAccountId(accountId);
            CartItem savedCartItem = getCartItemByCartIdAndProductId(cart.getId(), productId);
        return incrementQuantity(savedCartItem.getId());
    }

    @Override
    public int decreaseQuantity(int accountId, int productId) {
        Cart cart = cartService.getCartByAccountId(accountId);
        CartItem savedCartItem = getCartItemByCartIdAndProductId(cart.getId(), productId);
        return decrementQuantity(savedCartItem.getId());
    }

    @Override
    public CartItem getCartItemByCartIdAndProductId(int cartId, int productId) {
        CartItem savedCartItem = cartItemRepository.findByCartIdAndProductId(cartId, productId);
        return savedCartItem;
    }

    public Cart getCart(int accountId){
        logger.info("{}", accountId);
        Cart cart = cartService.getCartByAccountId(accountId);

//        logger.info("{}", cart.getAccount().getId());
        if(cart != null){
            return cart;
        }
        return null;
    }
    public void createCart(CartItem cartItem, Product product, int accountId){
        logger.info("Product ID: "+product.getId());

        Cart savedCart = cartService.createCart(accountId);

        cartItem.setAmount(product.getPrice());
        cartItem.setProduct(product);
        cartItem.setCart(savedCart);

        cartItemRepository.save(cartItem);
        logger.info("{}", cartItem.getId());

    }
    public int incrementQuantity(int cartItemId){
        CartItem savedCartItem = cartItemRepository.findById(cartItemId);
        savedCartItem.setQuantity(savedCartItem.getQuantity() + 1); // increment the quantity of existing item in cart item
        CartItem updatedCartItem = cartItemRepository.save(savedCartItem);
        return updatedCartItem.getQuantity();
    }
    public int decrementQuantity(int cartItemId){
        CartItem savedCartItem = cartItemRepository.findById(cartItemId);
        if(savedCartItem.getQuantity() > 1){
            savedCartItem.setQuantity(savedCartItem.getQuantity()-1);
            CartItem updatedCartItem = cartItemRepository.save(savedCartItem);
            return updatedCartItem.getQuantity();
        }
        throw new CartItemNotValidException("Not Valid");
    }
}
