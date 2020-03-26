package com.hmrc.ecom.orders.remote;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Service
public class ItemOperations {

    private static final Logger log = LoggerFactory.getLogger(ItemOperations.class);
    public static final String SKU_PARAM = "sku";

    private RestTemplate restTemplate;
    private Environment environment;

    public ItemOperations(RestTemplate restTemplate, Environment environment) {
        this.restTemplate = restTemplate;
        this.environment = environment;
    }

    public Item getItem(String sku) {

        try {
            String itemSvcUri = environment.getProperty("items-api.uri");

            Map<String, String> uriParams = new HashMap<>();
            uriParams.put(SKU_PARAM, sku);

            URI uri = UriComponentsBuilder.fromUriString(itemSvcUri).buildAndExpand(uriParams).toUri();

            log.info(String.format("Calling Item Service to fetch the Item details with uri %s", uri));
            ResponseEntity<Item> responseEntity = restTemplate.getForEntity(uri, Item.class);
            log.info(String.format("Successfully retrieved the item for the sku %s",sku));
            return responseEntity.getBody();
        } catch (HttpStatusCodeException e) {
            log.error(String.format("Failed to retrieve the item with sku %s",sku));
            return null;
        } catch (Exception e) {
            log.error(String.format("Failed to retrieve the item for the sku %s because of %s",sku, e.getMessage()));
            throw new HttpClientErrorException(HttpStatus.BAD_GATEWAY, e.getMessage());
        }

    }
}
