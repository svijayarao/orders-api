package com.hmrc.ecom.orders.processor;

import com.hmrc.ecom.orders.resource.response.ErrorResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

@Component
public class ExceptionProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorCode("2002");
        errorResponse.setErrorDescription("No Item Found with the given SKU");
        exchange.getIn().setBody(errorResponse);
    }
}
