package com.hmrc.ecom.orders.remote;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ItemOperationsTest {

    private ItemOperations itemOperations;

    @Mock
    private Environment environment;

    @Mock
    private RestTemplate restTemplate;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        itemOperations = new ItemOperations(restTemplate, environment);
    }

    @Test
    public void givenItemSku_WhenItemExists_ReturnsItem() throws URISyntaxException {

        Item expectedItem = new Item(1L, "1004", "ItemDesc", 200);
        ResponseEntity<Item> response = new ResponseEntity<>(expectedItem, HttpStatus.OK);

        when(environment.getProperty("items-api.uri")).thenReturn("itemsURI/{sku}");
        when(restTemplate.getForEntity(new URI("itemsURI/1004"), Item.class)).thenReturn(response);
        Item item = itemOperations.getItem("1004");
        Assert.assertEquals(expectedItem, item);

        verify(environment).getProperty("items-api.uri");
        verify(restTemplate).getForEntity(new URI("itemsURI/1004"), Item.class);
    }

    @Test
    public void givenItemSku_WhenItemDoesNotExists_ReturnsNull() throws URISyntaxException {

        Item expectedItem = new Item(1L, "1004", "ItemDesc", 200);
        ResponseEntity<Item> response = new ResponseEntity<>(expectedItem, HttpStatus.OK);

        when(environment.getProperty("items-api.uri")).thenReturn("itemsURI/{sku}");
        when(restTemplate.getForEntity(new URI("itemsURI/1004"), Item.class))
                .thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND, "No Item Found with SKU"));
        Assert.assertNull(itemOperations.getItem("1004"));

        verify(environment).getProperty("items-api.uri");
        verify(restTemplate).getForEntity(new URI("itemsURI/1004"), Item.class);
    }

    @Test(expected = HttpClientErrorException.class)
    public void givenItemSku_WhenItemServiceReturnsException_BadGatewayIsThrown() throws URISyntaxException {

        when(environment.getProperty("items-api.uri")).thenReturn("itemsURI/{sku}");
        when(restTemplate.getForEntity(new URI("itemsURI/1004"), Item.class))
                .thenThrow(new RuntimeException("Cannot process request"));

        itemOperations.getItem("1004");

        verify(environment).getProperty("items-api.uri");
        verify(restTemplate).getForEntity(new URI("itemsURI/1004"), Item.class);
    }
}
