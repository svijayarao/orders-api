package com.hmrc.ecom.orders.service;

import com.hmrc.ecom.orders.model.Order;
import com.hmrc.ecom.orders.remote.Item;
import com.hmrc.ecom.orders.remote.ItemOperations;
import com.hmrc.ecom.orders.repository.OrderRepository;
import com.hmrc.ecom.orders.service.exception.ItemNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.hamcrest.CoreMatchers.is;

public class OrderServiceTest {

    private OrderService orderService;

    @Mock
    private OrderRepository orderRepositoryMock;

    @Mock
    private ItemOperations itemOperationsMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        orderService = new OrderService(itemOperationsMock, orderRepositoryMock);
    }

    @Test
    public void createsOrder() {

        Order order = constructOrder();

        Mockito.when(itemOperationsMock.getItem("12345"))
                .thenReturn(new Item(1L, "12345", "Item 1", 25.0));
        Mockito.when(orderRepositoryMock.save(Mockito.any(Order.class)))
                .thenReturn(Mockito.any(Order.class));

        Order actualOrder = orderService.createOrder(order);

        Mockito.verify(itemOperationsMock).getItem("12345");
        Mockito.verify(orderRepositoryMock).save(Mockito.any(Order.class));

        Assert.assertNotNull(actualOrder.getOrderId());
        Assert.assertThat(actualOrder.getOrderTotal(), is(75.0));
    }

    @Test(expected = ItemNotFoundException.class)
    public void failsToCreatesOrderWhenItemSkuIsInvalid() {

        Order order = constructOrder();

        Mockito.when(itemOperationsMock.getItem("12345"))
                .thenReturn(null);

        orderService.createOrder(order);

        Mockito.verify(itemOperationsMock).getItem("12345");
        Mockito.verify(orderRepositoryMock, Mockito.times(0)).save(Mockito.any(Order.class));

    }

    @After
    public void tearDown() throws Exception {
        orderService = null;
    }

    private Order constructOrder() {
        Order order = new Order();
        order.setItemSku("12345");
        order.setNumberOfUnits(3);
        order.setOrderDesc("Order Description");
        return order;
    }

}
