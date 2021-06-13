package com.project.inventory.cart.cartItem.service.impl;

import com.project.inventory.cart.cartItem.model.CartItem;
import com.project.inventory.cart.cartItem.repository.CartItemRepository;
import com.project.inventory.cart.cartItem.service.CartItemService;
import com.project.inventory.cart.model.Cart;
import com.project.inventory.cart.service.CartService;
import com.project.inventory.exception.cartItem.CartItemNotFoundException;
import com.project.inventory.exception.cartItem.CartItemNotValidException;
import com.project.inventory.product.model.Product;
import com.project.inventory.product.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemServiceImpl implements CartItemService {
    Logger logger = LoggerFactory.getLogger(CartItemServiceImpl.class);

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    private int tempId = 1;

    @Override
    public void addCartItem(int productId, CartItem cartItem) {
        // get the product
        // get the cart that exist with account id
        Product product = productService.getAvailableProductById(productId);
        Cart cart = getCart(tempId);

        if(cart == null){
            // will create new cart
            // save the cart item
            createCart(cartItem, product, tempId);
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
        return cartItemRepository.findByCartIdAndProductId(cartId, productId);
    }

    @Override
    public void removeItems(List<CartItem> cartItems) {
        Cart cart = getCart(tempId);
        for(CartItem cartItem : cartItems){
            CartItem item = getCartItemByIdAndCartId(cartItem.getId(), cart.getId());
            deleteById(item.getId());
        }
    }

    @Override
    public void removeItem(int itemId) {
       Cart cart = getCart(tempId);
       CartItem item = getCartItemByIdAndCartId(itemId, cart.getId());
       logger.info("item :"+item.getId());
       deleteById(item.getId());
    }

    @Override
    public void deleteById(int id) {
        try{
            cartItemRepository.deleteById(id);
        }catch(CartItemNotValidException cartItemNotValidException){
            throw new CartItemNotFoundException("You can't Delete Remove this item with ID: "+ id);
        }

    }

    @Override
    public CartItem getCartItemById(int id) {
        return cartItemRepository.findById(id).orElseThrow(()-> new CartItemNotFoundException("Cart Item Not Found with ID: "+ id));
    }

    public Cart getCart(int accountId){
        Cart cart = cartService.getCartByAccountId(accountId);

        if(cart != null){
            logger.info("Cart ID: " +cart.getId());
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
        CartItem savedCartItem = getCartItemById(cartItemId);

        savedCartItem.setQuantity(savedCartItem.getQuantity() + 1); // increment the quantity of existing item in cart item

        // existing amount of item plus the product price
        // and save the new amount when incrementing the quantity
        savedCartItem.setAmount(savedCartItem.getAmount() + savedCartItem.getProduct().getPrice());

        CartItem updatedCartItem = cartItemRepository.save(savedCartItem);
        return updatedCartItem.getQuantity();
    }

    public int decrementQuantity(int cartItemId){
        CartItem savedCartItem = getCartItemById(cartItemId);

        if(savedCartItem.getQuantity() > 1){

            savedCartItem.setQuantity(savedCartItem.getQuantity()-1);

            // existing amount of item minus the product price
            // and save the new amount when incrementing the quantity
            savedCartItem.setAmount(savedCartItem.getAmount() - savedCartItem.getProduct().getPrice());

            CartItem updatedCartItem = cartItemRepository.save(savedCartItem);
            return updatedCartItem.getQuantity();
        }
        throw new CartItemNotValidException("You Reach the Limitation of Quantity");
    }

    public CartItem getCartItemByIdAndCartId(int id, int cartId) { // get the item with cart id
        return cartItemRepository.findByIdAndCartId(id, cartId)
                .orElseThrow(() -> new CartItemNotFoundException("Item not Found with ID"+ id) );
    }
}
