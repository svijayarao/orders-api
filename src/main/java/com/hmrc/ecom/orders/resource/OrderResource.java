package com.hmrc.ecom.orders.resource;

import com.hmrc.ecom.orders.model.Order;
import com.hmrc.ecom.orders.processor.ExceptionProcessor;
import com.hmrc.ecom.orders.processor.OrderProcessor;
import com.hmrc.ecom.orders.resource.request.OrderRequest;
import com.hmrc.ecom.orders.resource.response.ErrorResponse;
import com.hmrc.ecom.orders.resource.response.OrderResponse;
import com.hmrc.ecom.orders.resource.validator.RequestValidator;
import com.hmrc.ecom.orders.service.OrderService;
import com.hmrc.ecom.orders.service.exception.ItemNotFoundException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.model.rest.RestBindingMode;
import org.restlet.resource.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class OrderResource extends RouteBuilder {

    @Autowired
    private RequestValidator requestValidator;

    @Autowired
    private OrderProcessor orderProcessor;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ExceptionProcessor exceptionProcessor;

    @Override
    public void configure() throws Exception {

        restConfiguration().component("servlet").bindingMode(RestBindingMode.json);

        GsonDataFormat requestFormat = new GsonDataFormat(Order.class);
        GsonDataFormat errorResponseFormat = new GsonDataFormat(ErrorResponse.class);

        onException(ItemNotFoundException.class).handled(true)
            .process(exceptionProcessor)
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE));

        rest().get("{{orders-api.uri}}")
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .route().setBody(() -> orderService.getOrders());

        rest().post("{{orders-api.uri}}")
            .produces(MediaType.APPLICATION_JSON_VALUE)
            .consumes(MediaType.APPLICATION_JSON_VALUE)
                .type(OrderRequest.class)
                .outType(OrderResponse.class)
                .route()
                .log("The body received is ${body}")
                    .marshal(requestFormat)
                    .process(requestValidator)
                    .choice()
                        .when(exchange -> {
                            Object responseCode = exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE);
                            return responseCode != null && responseCode.equals(400);
                        })
                        .marshal(errorResponseFormat)
                        .stop()
                        .otherwise()
                    .log("Request validation completed ${body}")
                    .unmarshal(requestFormat)
                    .process(orderProcessor)
                    .log("Created order ${body} successfully")
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.CREATED.value()));
    }
}
