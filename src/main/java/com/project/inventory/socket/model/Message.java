package com.project.inventory.socket.model;

import java.util.Map;

public class Message {

    private String from;
    private String eventType;
    private Map<String, Object> message;

    public String getFrom() {
        return from;
    }

    public void setFrom( String from ) {
        this.from = from;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType( String eventType ) {
        this.eventType = eventType;
    }

    public Map<String, Object> getMessage() {
        return message;
    }

    public void setMessage( Map<String, Object> message ) {
        this.message = message;
    }
}