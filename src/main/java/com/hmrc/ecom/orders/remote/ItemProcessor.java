package com.hmrc.ecom.orders.remote;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

@Component
public class ItemProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        String sku = exchange.getIn().getHeader("sku", String.class);
        if (sku.equalsIgnoreCase("invalidSku")) {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND, "No Item Found with SKU "+sku);
        } else {
            exchange.getIn().setBody(new Item(1001L, sku, "Apple iPhone X", 1000));
        }
    }
}
