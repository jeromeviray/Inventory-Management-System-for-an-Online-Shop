package com.project.inventory.api.payment;


import com.project.inventory.api.RestClient;

import java.util.Map;

public class PaymongoAPI {

    private RestClient paymongoClient;

    public PaymongoAPI() {
        this.paymongoClient = new RestClient("https://api.paymongo.com/v1/");
        this.paymongoClient.headers.add("Authorization", "Basic c2tfdGVzdF9RbTN6YU1pY2VUZ3Rqc1BkY0dxa0Ruemo6c2Rm");
    }

    public Map generateSource( Float amount, String currency, String successUrl, String failedUrl) {
        String json = String.format(
                "{ \"data\": { \"attributes\": { \"amount\": %f, \"redirect\": { \"success\": \"%s\", \"failed\": \"%s\" }, \"type\": \"gcash\", \"currency\": \"%s\" } } }",
                amount,
                currency,
                successUrl,
                failedUrl
        );
        return this.paymongoClient.post("sources", json);
    }
}
