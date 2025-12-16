package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.entity.Order;
import com.valeri.project_RBPO.entity.OrderItem;
import com.valeri.project_RBPO.entity.Product;
import com.valeri.project_RBPO.model.OrderItemDto;
import com.valeri.project_RBPO.repository.OrderItemRepository;
import com.valeri.project_RBPO.repository.OrderRepository;
import com.valeri.project_RBPO.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    // Получить все позиции заказа
    @Transactional(readOnly = true)
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    // Получить позицию заказа по ID
    @Transactional(readOnly = true)
    public OrderItem getOrderItemById(UUID id) {
        return orderItemRepository.findById(id).orElse(null);
    }

    // Создать новую позицию заказа
    public OrderItem createOrderItem(OrderItemDto orderItemDto) {
        Order order = orderRepository.findById(orderItemDto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        Product product = productRepository.findById(orderItemDto.getProductId())
                .orElseThrow(() -> new RuntimeException("Товар не найден"));

        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(product.getPrice()); // Используем текущую цену товара

        return orderItemRepository.save(orderItem);
    }

    // Обновить позицию заказа
    public OrderItem updateOrderItem(UUID id, OrderItemDto orderItemDto) {
        OrderItem orderItem = orderItemRepository.findById(id).orElse(null);
        if (orderItem != null) {
            // Обновляем заказ, если указан
            if (orderItemDto.getOrderId() != null) {
                Order order = orderRepository.findById(orderItemDto.getOrderId())
                        .orElseThrow(() -> new RuntimeException("Заказ не найден"));
                orderItem.setOrder(order);
            }

            // Обновляем товар, если указан
            if (orderItemDto.getProductId() != null) {
                Product product = productRepository.findById(orderItemDto.getProductId())
                        .orElseThrow(() -> new RuntimeException("Товар не найден"));
                orderItem.setProduct(product);
                orderItem.setPrice(product.getPrice());
            }

            // Обновляем количество, если указано
            if (orderItemDto.getQuantity() != null) {
                orderItem.setQuantity(orderItemDto.getQuantity());
            }

            return orderItemRepository.save(orderItem);
        }
        return null;
    }

    // Удалить позицию заказа
    public boolean deleteOrderItem(UUID id) {
        try {
            int deletedCount = orderItemRepository.deleteByIdNative(id);
            return deletedCount > 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional(readOnly = true)
    public List<OrderItem> getOrderItemsByOrderId(UUID orderId) {
        return orderItemRepository.findByOrderId(orderId);
    }
}