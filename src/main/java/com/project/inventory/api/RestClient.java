package com.project.inventory.api;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class RestClient {

    private final String server;
    private final RestTemplate rest;
    public HttpHeaders headers;
    private HttpStatus status;

    public RestClient(String server) {
        this.server = server;
        this.rest = new RestTemplate();
        this.headers = new HttpHeaders();
        this.headers.add("Content-Type", "application/json");
        this.headers.add("Accept", "*/*");
    }

    public Map get(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", this.headers);
        ResponseEntity<Map> responseEntity = this.rest.exchange(this.server + uri, HttpMethod.GET, requestEntity, Map.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public Map post(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, this.headers);
        ResponseEntity<Map> responseEntity = this.rest.exchange(this.server + uri, HttpMethod.POST, requestEntity, Map.class);
        this.setStatus(responseEntity.getStatusCode());
        return responseEntity.getBody();
    }

    public void put(String uri, String json) {
        HttpEntity<String> requestEntity = new HttpEntity<String>(json, this.headers);
        ResponseEntity<String> responseEntity = this.rest.exchange(this.server + uri, HttpMethod.PUT, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
    }

    public void delete(String uri) {
        HttpEntity<String> requestEntity = new HttpEntity<String>("", this.headers);
        ResponseEntity<String> responseEntity = this.rest.exchange(this.server + uri, HttpMethod.DELETE, requestEntity, String.class);
        this.setStatus(responseEntity.getStatusCode());
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
