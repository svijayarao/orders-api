package com.hmrc.ecom.orders.repository;

import com.hmrc.ecom.orders.model.Order;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class OrderRepository {

    private List<Order> orders = new ArrayList<>();

    @PostConstruct
    public void initOrders() {
        orders.add(new Order(UUID.randomUUID().toString(),"Samsung Galaxy", 800, "1001", 2));
        orders.add(new Order(UUID.randomUUID().toString(),"iPhone 8", 1000, "1002", 2));
    }

    public List<Order> getOrders() {
        return orders;
    }

    public Order save(Order order) {
        orders.add(order);
        return order;
    }
}
