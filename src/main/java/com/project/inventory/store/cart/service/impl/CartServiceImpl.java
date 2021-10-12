package com.project.inventory.store.cart.service.impl;

import com.project.inventory.common.permission.model.Account;
import com.project.inventory.common.permission.service.AccountService;
import com.project.inventory.exception.notFound.NotFoundException;
import com.project.inventory.exception.notFound.cart.CartNotFound;
import com.project.inventory.store.cart.cartItem.model.CartItem;
import com.project.inventory.store.cart.cartItem.model.CartItemDto;
import com.project.inventory.store.cart.cartItem.model.ProductCartItemDto;
import com.project.inventory.store.cart.cartItem.service.impl.CartItemServiceImpl;
import com.project.inventory.store.cart.model.Cart;
import com.project.inventory.store.cart.model.CartDto;
import com.project.inventory.store.cart.repository.CartRepository;
import com.project.inventory.store.cart.service.CartService;
import com.project.inventory.store.inventory.model.Inventory;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.product.model.ProductAndInventoryDto;
import com.project.inventory.store.product.promo.model.Promo;
import com.project.inventory.store.product.promo.service.PromoService;
import com.project.inventory.store.product.service.ProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    Logger logger = LoggerFactory.getLogger( CartServiceImpl.class );

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private AccountService accountService;
    @Autowired
    private CartItemServiceImpl cartItemServiceimpl;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private ProductService productService;
    @Autowired
    private PromoService promoService;

    @Override
    public Cart getCartByCartIdAndAccountId( int cartId, int accountId ) {
        return cartRepository.findByIdAndAccountId( cartId, accountId )
                .orElseThrow( () -> new CartNotFound( String.format( "Cart Not Found with ID: {}", cartId ) ) );
    }

    @Override
    public Cart createCart( int accountId ) {

        Account account = accountService.getAccountById( accountId );
        Cart cart = new Cart();

        if ( account == null ) throw new NotFoundException( String.format( "Account Not Found! " + accountId ) );

        cart.setAccount( account );
        logger.info( "{}", cart + " created successfully" );

        return cartRepository.save( cart );

    }

    @Override
    public Cart getCartByAccountId( int accountId ) {
        return cartRepository.findByAccountId( accountId );
    }

    @Override
    public CartDto getCartByAccountIdDto( int accountId ) throws ParseException {
        Cart cart = cartRepository.findByAccountId( accountId );
        List<CartItemDto> cartItems = new ArrayList<>();
        for ( CartItem item : cart.getCartItems() ) {
            CartItemDto cartItem = new CartItemDto();
            cartItem.setId( item.getId() );
            cartItem.setAmount( item.getAmount() );
            cartItem.setAddedAt( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format( item.getAddedAt() ) );
            cartItem.setQuantity( item.getQuantity() );
            InventoryDto inventory = inventoryService.convertEntityToDto(
                    inventoryService.getInventoryByProductId(
                            item.getProduct().getId() ) );
            ProductAndInventoryDto product = new ProductAndInventoryDto();
            product.setInventory( inventory );
            product.setProduct( productService.convertEntityToDto( item.getProduct() ) );
            if ( item.getProduct().getPromo() != null ) {
                Promo promo = item.getProduct().getPromo();
                promo.setStatus( promoService.checkSchedulePromo( promo ) );
                promoService.updatePromo( promo.getId(), promo );
                product.setPromo( promoService.convertEntityToDto( promo ) );
            }
            cartItem.setProduct( product );
            cartItems.add( cartItem );
        }
        CartDto cartDto = new CartDto();
        cartDto.setCartId( cart.getId() );
        cartDto.setAccountId( accountId );
        cartDto.setCartItems( cartItems );
        return cartDto;
    }

    @Override
    public Cart getCartById( int id ) {
        return cartRepository.findById( id )
                .orElseThrow( () -> new CartNotFound( String.format( "Cart Not Found with ID: " + id ) ) );
    }

    @Override
    public List<CartItem> getCartProducts( int cartId ) {
        List<CartItem> cartItems = new ArrayList<>();

        Cart cart = getCartById( cartId );

        for ( CartItem cartItem : cart.getCartItems() ) {
            cartItems.add( cartItem );
        }

        return cartItems;
    }

    // converting entity to dto
    @Override
    public CartDto convertEntityToDto( Cart cart ) {
        return mapper.map( cart, CartDto.class );
    }

    // converting dto to entity
    @Override
    public Cart convertDtoToEntity( CartDto cartDto ) {
        return mapper.map( cartDto, Cart.class );
    }
}
