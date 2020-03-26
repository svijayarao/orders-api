package com.hmrc.ecom.orders.resource.validator;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jsonschema.core.report.ListProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.hmrc.ecom.orders.resource.response.ErrorResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class RequestValidator implements Processor {

    private JsonSchemaFactory jsonSchemaFactory;

    private ObjectMapper objectMapper;

    public RequestValidator(JsonSchemaFactory jsonSchemaFactory, ObjectMapper objectMapper) {
        this.jsonSchemaFactory = jsonSchemaFactory;
        this.objectMapper = objectMapper;
    }

    @Override
    public void process(Exchange exchange) throws Exception {

        JsonNode jsonSchema = JsonLoader.fromResource("/order-schema.json");
        JsonNode request = objectMapper.readTree(exchange.getIn().getBody(String.class));
        ListProcessingReport report = (ListProcessingReport) jsonSchemaFactory.getValidator().validate(jsonSchema, request);

        if (report != null && !report.isSuccess()) {
            Message msg = exchange.getIn();
            msg.setHeader(Exchange.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
            msg.setHeader(Exchange.HTTP_RESPONSE_CODE, 400);

            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorCode("4004");
            errorResponse.setErrorDescription(report.asJson().findValue("message").asText());

            msg.setBody(errorResponse);
        }
    }
}
