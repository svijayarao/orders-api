package com.hmrc.ecom.orders.resource;

import com.hmrc.ecom.orders.model.Order;
import com.hmrc.ecom.orders.resource.response.ErrorResponse;
import com.hmrc.ecom.orders.resource.response.OrderResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class OrderResourceITest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void givenOrderRequest_WhenInputIsValidAndItemExists_CreatesOrder() {

        String request = "{\n" +
                "        \"orderDesc\": \"Apple iPhone X\",\n" +
                "        \"itemSku\": 1003,\n" +
                "        \"numberOfUnits\": 2\n" +
                "}";

        ResponseEntity<OrderResponse> responseEntity = restTemplate.postForEntity("/orders-api/orders", request, OrderResponse.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getOrderId());
        assertThat(responseEntity.getBody().getOrderTotal(), is(2000.0));
    }

    @Test
    public void givenOrderRequest_WhenNoItemExists_FailsToCreateOrder() {

        String request = "{\n" +
                "        \"orderDesc\": \"Apple iPhone X\",\n" +
                "        \"itemSku\": \"invalidSku\",\n" +
                "        \"numberOfUnits\": 2\n" +
                "}";

        ResponseEntity<ErrorResponse> responseEntity = restTemplate.postForEntity("/orders-api/orders", request, ErrorResponse.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getErrorCode(), is("2002"));
        assertThat(responseEntity.getBody().getErrorDescription(), is("No Item Found with the given SKU"));
    }

    @Test
    public void givenOrderRequest_WhenRequestIsInvalid_FailsToCreateOrder() {

        String request = "{\n" +
                "        \"orderDesc\": \"Apple iPhone X\",\n" +
                "        \"numberOfUnits\": 2\n" +
                "}";

        ResponseEntity<ErrorResponse> responseEntity = restTemplate.postForEntity("/orders-api/orders", request, ErrorResponse.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));
        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getErrorCode(), is("4004"));
    }

    @Test
    public void given_WhenOrdersExists_GetsListOfOrders() {

        ResponseEntity<List<Order>> responseEntity = restTemplate.exchange("/orders-api/orders", HttpMethod.GET, null, new ParameterizedTypeReference<List<Order>>(){});
        assertNotNull(responseEntity);
        assertTrue(responseEntity.getBody().size() > 0);

    }

}
