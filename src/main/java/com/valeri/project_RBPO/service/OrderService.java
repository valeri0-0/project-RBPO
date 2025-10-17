package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.model.Order;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderService {
    private List<Order> orders = new ArrayList<>();
    private Long nextId = 1L;

    public List<Order> getAllOrders() {
        return new ArrayList<>(orders);
    }

    public Order getOrderById(Long id) {
        return orders.stream()
                .filter(o -> o.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Order createOrder(Order order) {
        order.setId(nextId++);
        orders.add(order);
        return order;
    }

    public Order updateOrder(Long id, Order orderDetails) {
        Order order = getOrderById(id);
        if (order != null) {
            order.setCustomerId(orderDetails.getCustomerId());
            order.setStatus(orderDetails.getStatus());
        }
        return order;
    }

    public boolean deleteOrder(Long id) {
        return orders.removeIf(o -> o.getId().equals(id));
    }
}