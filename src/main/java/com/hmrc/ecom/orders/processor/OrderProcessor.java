package com.hmrc.ecom.orders.processor;

import com.hmrc.ecom.orders.model.Order;
import com.hmrc.ecom.orders.service.OrderService;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class OrderProcessor implements Processor {

    private static final Logger log = LoggerFactory.getLogger(OrderProcessor.class);

    private OrderService orderService;

    public OrderProcessor(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void process(Exchange exchange) throws Exception {
        orderService.createOrder(exchange.getIn().getBody(Order.class));
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
    }
}
