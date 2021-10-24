package com.project.inventory.socket.model;

import java.util.Map;

public class OutputMessage {
    String from;
    String eventType;
    Map<String, Object> message;
    String time;

    public OutputMessage( String from, Map<String, Object> message, String time, String eventType ) {
        this.from = from;
        this.message = message;
        this.eventType = eventType;
        this.time = time;
    }

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

    public String getTime() {
        return time;
    }

    public void setTime( String time ) {
        this.time = time;
    }
}
