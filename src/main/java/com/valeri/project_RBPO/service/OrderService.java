package com.valeri.project_RBPO.service;

import com.valeri.project_RBPO.entity.*;
import com.valeri.project_RBPO.model.OrderDto;
import com.valeri.project_RBPO.model.OrderItemDto;
import com.valeri.project_RBPO.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;


@Service
@RequiredArgsConstructor
@Transactional
public class OrderService
{
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<Order> getAllOrders()
    {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order getOrderById(UUID id)
    {
        return orderRepository.findById(id).orElse(null);
    }

    public Order createOrder(OrderDto orderDto)
    {
        Customer customer = customerRepository.findById(orderDto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Покупатель не найден с ID: " + orderDto.getCustomerId()));

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(orderDto.getStatus() != null ? orderDto.getStatus() : "NEW");
        return orderRepository.save(order);
    }

    public boolean deleteOrder(UUID id)
    {
        if (orderRepository.existsById(id))
        {
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Создание заказа с позициями
    public Order createOrderWithItems(UUID customerId, List<OrderItemDto> items)
    {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Покупатель не найден"));

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus("NEW");
        order = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemDto itemDto : items)
        {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new RuntimeException("Товар не найден"));

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemDto.getQuantity());
            orderItem.setPrice(product.getPrice());
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
        return orderRepository.save(order);
    }

    // Расчет суммы заказа
    @Transactional(readOnly = true)
    public Double calculateOrderTotal(UUID orderId)
    {
        return orderItemRepository.calculateOrderTotal(orderId);
    }

    // Оплата заказа
    public Order payOrder(UUID orderId)
    {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        if ("PAID".equals(order.getStatus()))
        {
            throw new RuntimeException("Заказ уже оплачен");
        }

        order.setStatus("PAID");
        return orderRepository.save(order);
    }

    // Заказы покупателя
    @Transactional(readOnly = true)
    public List<Order> getCustomerOrders(UUID customerId)
    {
        return orderRepository.findByCustomerId(customerId);
    }

    // Популярные товары
    @Transactional(readOnly = true)
    public List<Object[]> getPopularProductsData()
    {
        return orderItemRepository.findPopularProducts();
    }

    // Заказы по статусу
    @Transactional(readOnly = true)
    public List<Order> getOrdersByStatus(String status)
    {
        return orderRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Order> getCustomerOrdersByStatus(UUID customerId, String status)
    {
        return orderRepository.findByCustomerIdAndStatus(customerId, status);
    }

    // Обновить заказ
    public Order updateOrder(UUID id, OrderDto orderDto) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order != null) {
            // Если указан новый покупатель - обновляем
            if (orderDto.getCustomerId() != null) {
                Customer customer = customerRepository.findById(orderDto.getCustomerId())
                        .orElseThrow(() -> new RuntimeException("Покупатель не найден с ID: " + orderDto.getCustomerId()));
                order.setCustomer(customer);
            }

            // Если указан новый статус - обновляем
            if (orderDto.getStatus() != null) {
                order.setStatus(orderDto.getStatus());
            }

            return orderRepository.save(order);
        }
        return null;
    }

}