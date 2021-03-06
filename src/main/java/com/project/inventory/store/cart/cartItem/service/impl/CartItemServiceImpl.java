package com.project.inventory.store.cart.cartItem.service.impl;

import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.cartItem.model.CartItemDto;
import com.project.inventory.store.cart.cartItem.repository.CartItemRepository;
import com.project.inventory.store.cart.cartItem.service.CartItemService;
import com.project.inventory.store.cart.model.Cart;
import com.project.inventory.store.cart.service.CartService;
import com.project.inventory.exception.notFound.cart.CartItemNotFoundException;
import com.project.inventory.exception.invalid.cart.CartItemNotValidException;
import com.project.inventory.common.permission.service.AuthenticatedUser;
import com.project.inventory.store.product.model.Product;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;

@Service( value = "cartItemServiceImpl" )
public class CartItemServiceImpl implements CartItemService {
    Logger logger = LoggerFactory.getLogger( CartItemServiceImpl.class );

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private AuthenticatedUser authenticatedUser;

    @Override
    public void addCartItem( int productId ) throws ParseException {
        // get the product
        // get the cart that exist with account id
        Product product = productService.getAvailableProductById( productId );


        Cart cart = getCart( authenticatedUser.getUserDetails().getId() );
        CartItem cartItem = new CartItem();
        if ( cart == null ) {
            logger.info( "Cart is null. Processing new cart" );
            // when the user/customer is new and don't have cart
            // will create new cart
            // save the cart item

            createCart( cartItem, product, authenticatedUser.getUserDetails().getId() );
        } else {
            // if the user/customer has already Cart and item. the item will
            // just increment
            // get the saved item
            CartItem savedCartItem = getCartItemByCartIdAndProductId( cart.getId(), product.getId() );
            if ( savedCartItem != null ) {
                increaseQuantity( savedCartItem.getProduct().getId() );
            } else {
                //check if the product has promo and it's on going

                // save the new product in their cart
                cartItem.setProduct( product );
                cartItem.setAmount( product.getPrice() );
                cartItem.setQuantity( 1 );
                cartItem.setCart( cart );
                cartItemRepository.save( cartItem );
            }
        }
    }

    // item can be increment and decrement of the quantity
    // this is for api when the user is in shopping cart
    @Override
    public CartItem increaseQuantity( int productId ) throws ParseException {
        Cart cart = cartService.getCartByAccountId( authenticatedUser.getUserDetails().getId() );
        CartItem savedCartItem = getCartItemByCartIdAndProductId( cart.getId(), productId );
        if (  productService.getProductAndInventoryByProductId(
                        savedCartItem
                                .getProduct()
                                .getId() )
                .getInventory()
                .getTotalStock() > savedCartItem.getQuantity() ) {

            savedCartItem.setQuantity( savedCartItem.getQuantity() + 1 ); // increment the quantity of existing item in cart item
            // existing amount of item plus the product price
            // and save the new amount when incrementing the quantity
            savedCartItem.setAmount( savedCartItem.getAmount() + savedCartItem.getProduct().getPrice() );
            return cartItemRepository.save( savedCartItem );
        }else{
            throw new CartItemNotValidException( "You Reach the Limitation of Quantity" );
        }
    }

    @Override
    public CartItem decreaseQuantity( int productId ) {
        Cart cart = cartService.getCartByAccountId( authenticatedUser.getUserDetails().getId() );
        CartItem savedCartItem = getCartItemByCartIdAndProductId( cart.getId(), productId );
        if ( savedCartItem.getQuantity() > 1 ) {
            savedCartItem.setQuantity( savedCartItem.getQuantity() - 1 );
            // existing amount of item minus the product price
            // and save the new amount when incrementing the quantity
            savedCartItem.setAmount( savedCartItem.getAmount() - savedCartItem.getProduct().getPrice() );
            return cartItemRepository.save( savedCartItem );
        } else {
            throw new CartItemNotValidException( "You Reach the Limit of Quantity to be added in your cart. Please check your cart" );
        }
    }

    @Override
    public CartItem getCartItemByCartIdAndProductId( int cartId, int productId ) {
        return cartItemRepository.findByCartIdAndProductId( cartId, productId );
    }

    @Override
    public void removeItems( List<CartItemDto> cartItems ) {
        Cart cart = getCart( authenticatedUser.getUserDetails().getId() );
        for ( CartItemDto cartItem : cartItems ) {
            CartItem item = getCartItemByIdAndCartId( cartItem.getId(), cart.getId() );
            deleteById( item.getId() );
        }
    }

    @Override
    public void removeItem( int itemId ) {
        Cart cart = getCart( authenticatedUser.getUserDetails().getId() );
        CartItem item = getCartItemByIdAndCartId( itemId, cart.getId() );
        logger.info( "item :" + item.getId() );
        deleteById( item.getId() );
    }

    @Override
    public void deleteById( int id ) {
        try {
            cartItemRepository.deleteById( id );
        } catch ( CartItemNotValidException cartItemNotValidException ) {
            throw new CartItemNotFoundException( String.format( "You can't Delete Remove this item with ID: " + id ) );
        }

    }

    @Override
    public CartItem getCartItemById( int id ) {
        return cartItemRepository.findById( id ).orElseThrow( () -> new CartItemNotFoundException( String.format( "Cart Item Not Found with ID: " + id ) ) );
    }

    public Cart getCart( int accountId ) {
        Cart cart = cartService.getCartByAccountId( accountId );

        if ( cart != null ) {
//            logger.info(String.format("Cart ID: " +cart.getId()));
            return cart;
        }
        return null;
    }

    @Override
    public CartItemDto getCartItem( int id ) {
        CartItem cartItem = cartItemRepository.findById( id )
                .orElseThrow( () ->
                        new CartItemNotFoundException(
                                String.format( "Cart Item Not Found with ID: " + id ) ) );

        return convertEntityToDto( cartItem );

    }

    public void createCart( CartItem cartItem, Product product, int accountId ) {
        Cart savedCart = cartService.createCart( accountId );
        cartItem.setAmount( product.getPrice() );
        cartItem.setProduct( product );
        cartItem.setCart( savedCart );

        cartItemRepository.save( cartItem );
    }

//    public int incrementQuantity( int cartItemId ) {
//        CartItem savedCartItem = getCartItemById( cartItemId );
//
//        savedCartItem.setQuantity( savedCartItem.getQuantity() + 1 ); // increment the quantity of existing item in cart item
//
//        // existing amount of item plus the product price
//        // and save the new amount when incrementing the quantity
//        savedCartItem.setAmount( savedCartItem.getAmount() + savedCartItem.getProduct().getPrice() );
//
//        CartItem updatedCartItem = cartItemRepository.save( savedCartItem );
//        return updatedCartItem.getQuantity();
//    }

//    public int decrementQuantity( int cartItemId ) {
//        CartItem savedCartItem = getCartItemById( cartItemId );
//
//        if ( savedCartItem.getQuantity() > 1 ) {
//
//            savedCartItem.setQuantity( savedCartItem.getQuantity() - 1 );
//
//            // existing amount of item minus the product price
//            // and save the new amount when incrementing the quantity
//            savedCartItem.setAmount( savedCartItem.getAmount() - savedCartItem.getProduct().getPrice() );
//
//            CartItem updatedCartItem = cartItemRepository.save( savedCartItem );
//            return updatedCartItem.getQuantity();
//        }
//        throw new CartItemNotValidException( String.format( "You Reach the Limitation of Quantity" ) );
//    }

    public CartItem getCartItemByIdAndCartId( int id, int cartId ) { // get the item with cart id
        return cartItemRepository.findByIdAndCartId( id, cartId )
                .orElseThrow( () -> new CartItemNotFoundException( String.format( "Item not Found with ID: " + id ) ) );
    }

    // converting entity to dto
    public CartItemDto convertEntityToDto( CartItem cartItem ) {
        return mapper.map( cartItem, CartItemDto.class );
    }

    // converting dto to entity
    public CartItem convertDtoToEntity( CartItemDto cartItemDto ) {
        return mapper.map( cartItemDto, CartItem.class );
    }
}
