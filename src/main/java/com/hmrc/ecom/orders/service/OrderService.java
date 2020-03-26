package com.hmrc.ecom.orders.service;

import com.hmrc.ecom.orders.model.Order;
import com.hmrc.ecom.orders.remote.Item;
import com.hmrc.ecom.orders.remote.ItemOperations;
import com.hmrc.ecom.orders.repository.OrderRepository;
import com.hmrc.ecom.orders.service.exception.ItemNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private ItemOperations itemOperations;
    private OrderRepository orderRepository;

    public OrderService(ItemOperations itemOperations, OrderRepository orderRepository) {
        this.itemOperations = itemOperations;
        this.orderRepository = orderRepository;
    }

    public List<Order> getOrders() {
        return orderRepository.getOrders();
    }

    public Order createOrder(Order order) {

        Order processedOrder = populateOrder(order);
        log.info(String.format("Creating Order %s", processedOrder));
        orderRepository.save(processedOrder);
        return order;
    }

    private Order populateOrder(Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        log.info(String.format("Fetching the item details for the SKU %s", order.getItemSku()));
        Item item = itemOperations.getItem(order.getItemSku());

        if (item == null) {
            throw new ItemNotFoundException(String.format("No Item with SKU %s",order.getItemSku()));
        }
        order.setOrderTotal(order.getNumberOfUnits() * item.getPrice());
        return order;
    }

}
