package com.project.inventory.socket.controller;

import com.project.inventory.socket.model.Message;
import com.project.inventory.socket.model.OutputMessage;
import com.project.inventory.store.inventory.service.impl.InventoryServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@MessageMapping("/websocket")
public class InventoryWebSocketController {
    Logger logger = LoggerFactory.getLogger( InventoryWebSocketController.class );

    @MessageMapping("/inventory")
    @SendTo("/topic/messages")
    public OutputMessage send( Message message) throws Exception {
        logger.info("GOT MESSAGE");
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        return new OutputMessage(message.getFrom(), message.getText(), time);
    }
}
