package com.project.inventory.socket.controller;

import com.project.inventory.socket.model.Message;
import com.project.inventory.socket.model.OutputMessage;
import com.project.inventory.store.cart.cartItem.repository.CartItemRepository;
import com.project.inventory.store.cart.repository.CartRepository;
import com.project.inventory.store.inventory.model.InventoryDto;
import com.project.inventory.store.inventory.service.InventoryService;
import com.project.inventory.store.inventory.service.impl.InventoryServiceImpl;
import com.project.inventory.store.product.model.Product;
import org.apache.http.client.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@MessageMapping("/websocket")
public class InventoryWebSocketController {
    Logger logger = LoggerFactory.getLogger( InventoryWebSocketController.class );

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private InventoryService inventoryService;

    @MessageMapping("/inventory")
    @SendTo("/topic/messages")
    public OutputMessage send( Message message) throws Exception {
        Map<String, Object> data = message.getMessage();
        logger.info("GOT MESSAGE: {}", message.getEventType());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        switch( message.getEventType() ) {
            case "checkout":
                data = this.checkout( data );
                break;
            case "release_checkout":
                this.releaseCheckoutLock( data );
                break;
        }
        return new OutputMessage(message.getFrom(), data, time, message.getEventType());
    }

    private Map<String, Object> checkout(Map<String, Object> data) {
        logger.info("Cart id: {}", data.get("cartId"));
        List<Map<String, Object>> items = ( ArrayList<Map<String, Object>>) data.get( "items" );
        int addMinuteTime = 8;
        Calendar currentTimeNow = Calendar.getInstance();
        currentTimeNow.add(Calendar.MINUTE, addMinuteTime);
        Date cartExpiration = currentTimeNow.getTime();
        List<Map<String, Object>> newItems = new ArrayList<Map<String, Object>>();

        //cartRepository.updateCartCheckoutExpiration( (Integer ) data.get("cartId"), (Integer) data.get("accountId"), cartExpiration );
        for( Map<String, Object> item : items) {
            Map<String, Object> productDetails = (Map<String, Object>) item.get("product");
            Map<String, Object> product = (Map<String, Object>) productDetails.get("product");

            Integer productId = (Integer) product.get("id");

            Product prod = new Product();
            prod.setId( productId );

            Integer totalStock = inventoryService.getTotalStocks(prod);
            Date cartExpiration2 = cartExpiration;
            Integer itemQuantity = (Integer) item.get("quantity");
            if(itemQuantity > totalStock ) {
                cartExpiration2 = null;
            } else {
                Map<String, Object> inventoryData = (Map<String, Object>) productDetails.get("inventory");
                inventoryData.put("totalStock", totalStock - itemQuantity);
                productDetails.put("inventory", inventoryData);
            }
            newItems.add( productDetails );
            logger.info( "Cart Item: {} {} {}", item.get("id"), product.get("id"), product.get("productName") );
            cartItemRepository.updateCartCheckoutExpiration( (Integer ) data.get("cartId"), (Integer) product.get("id"), cartExpiration2 );
        }
        data.put("items", newItems);
        return data;
    }

    private void releaseCheckoutLock(Map<String, Object> data) {
        logger.info("Cart id: {}", data.get("cartId"));
        List<Map<String, Object>> items = ( ArrayList<Map<String, Object>>) data.get( "items" );
        cartRepository.updateCartCheckoutExpiration( (Integer ) data.get("cartId"), (Integer) data.get("accountId"), null );
        for( Map<String, Object> item : items) {
            Map<String, Object> product = (Map<String, Object>) item.get("product");
            product = (Map<String, Object>) product.get("product");
            logger.info( "Cart Item: {} {} {}", item.get("id"), product.get("id"), product.get("productName") );
            cartItemRepository.updateCartCheckoutExpiration( (Integer ) data.get("cartId"), (Integer) product.get("id"), null );
        }
    }

}
