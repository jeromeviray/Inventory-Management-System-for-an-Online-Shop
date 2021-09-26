package com.project.inventory.api.payment;


import com.project.inventory.api.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DecimalFormat;
import java.util.Map;

public class PaymongoAPI {
    Logger logger = LoggerFactory.getLogger( PaymongoAPI.class );

    private RestClient paymongoClient;

    public PaymongoAPI() {
        this.paymongoClient = new RestClient("https://api.paymongo.com/v1/");
        this.paymongoClient.headers.add("Authorization", "Basic c2tfdGVzdF9RbTN6YU1pY2VUZ3Rqc1BkY0dxa0Ruemo6c2Rm");
    }

    public Map generateSource( double amount, String currency, String successUrl, String failedUrl) {
        Integer totalAmount = Integer.valueOf(String.format("%.2f", amount).replace(".", ""));
        String json = String.format(
                "{ \"data\": { \"attributes\": { \"amount\": %d, \"redirect\": { \"success\": \"%s\", \"failed\": \"%s\" }, \"type\": \"gcash\", \"currency\": \"%s\" } } }",
                totalAmount,
                successUrl,
                failedUrl,
                currency
        );
        logger.info(json);
        return this.paymongoClient.post("sources", json);
    }
}
