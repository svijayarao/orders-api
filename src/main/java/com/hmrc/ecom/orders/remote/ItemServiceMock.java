package com.hmrc.ecom.orders.remote;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class ItemServiceMock extends RouteBuilder {

    private static final Logger log = LoggerFactory.getLogger(ItemServiceMock.class);

    @Autowired
    private ItemProcessor itemProcessor;

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        rest().get("/items-api/items/{sku}")
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .route()
            .process(itemProcessor);

    }

}
