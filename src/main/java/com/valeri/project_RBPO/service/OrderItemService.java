package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.model.OrderItem;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class OrderItemService {
    private List<OrderItem> orderItems = new ArrayList<>();
    private Long nextId = 1L;

    public List<OrderItem> getAllOrderItems() {
        return new ArrayList<>(orderItems);
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItems.stream()
                .filter(oi -> oi.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        orderItem.setId(nextId++);
        orderItems.add(orderItem);
        return orderItem;
    }

    public OrderItem updateOrderItem(Long id, OrderItem orderItemDetails) {
        OrderItem orderItem = getOrderItemById(id);
        if (orderItem != null) {
            orderItem.setOrderId(orderItemDetails.getOrderId());
            orderItem.setProductId(orderItemDetails.getProductId());
            orderItem.setQuantity(orderItemDetails.getQuantity());
        }
        return orderItem;
    }

    public boolean deleteOrderItem(Long id) {
        return orderItems.removeIf(oi -> oi.getId().equals(id));
    }
}