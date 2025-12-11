package com.valeri.project_RBPO.controller;

import com.valeri.project_RBPO.entity.Order;
import com.valeri.project_RBPO.entity.Product;
import com.valeri.project_RBPO.model.OrderDto;
import com.valeri.project_RBPO.model.OrderItemDto;
import com.valeri.project_RBPO.model.PopularProductDto;
import com.valeri.project_RBPO.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
public class OrderOperationsController
{
    private final OrderService orderService;

     // Получить все заказы
    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders()
    {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

     // Получить заказ по ID
     @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable UUID id)
     {
        Order order = orderService.getOrderById(id);
        return order != null ? ResponseEntity.ok(order) : ResponseEntity.notFound().build();
     }

    // Создать простой заказ
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDto orderDto)
    {
        try {
            Order order = orderService.createOrder(orderDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(order);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Удалить заказ
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id)
    {
        return orderService.deleteOrder(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // БИЗНЕС-ОПЕРАЦИИ
    // Создать заказ с позициями
    @PostMapping("/customer/{customerId}/items")
    public ResponseEntity<?> createOrderWithItems(@PathVariable UUID customerId,
                                                      @RequestBody List<OrderItemDto> items)
    {
        try
        {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(orderService.createOrderWithItems(customerId, items));
        } catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Рассчитать сумму заказа
     @GetMapping("/{orderId}/total")
    public ResponseEntity<Double> getOrderTotal(@PathVariable UUID orderId)
     {
        return ResponseEntity.ok(orderService.calculateOrderTotal(orderId));
     }

    // Оплатить заказ
    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Object> payOrder(@PathVariable UUID orderId)
    {
        try
        {
            return ResponseEntity.ok(orderService.payOrder(orderId));
        } catch (RuntimeException e)
        {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Получить заказы покупателя
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getCustomerOrders(@PathVariable UUID customerId)
    {
        return ResponseEntity.ok(orderService.getCustomerOrders(customerId));
    }

    // Получить заказы по статусу
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Order>> getOrdersByStatus(@PathVariable String status)
    {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status));
    }

    // Получить популярные товары
    @GetMapping("/analytics/popular-products")
    public ResponseEntity<List<PopularProductDto>> getPopularProducts()
    {
        try {
            List<Object[]> popularProductsData = orderService.getPopularProductsData();
            List<PopularProductDto> result = popularProductsData.stream()
                    .map(data -> {
                        PopularProductDto dto = new PopularProductDto();
                        dto.setProductName(((Product) data[0]).getName());
                        dto.setTotalSold((Long) data[1]);
                        dto.setTotalRevenue((Double) data[2]);
                        return dto;
                    })
                    .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Получить заказы покупателя по статусу
    @GetMapping("/customer/{customerId}/status/{status}")
    public ResponseEntity<List<Order>> getCustomerOrdersByStatus(
            @PathVariable UUID customerId,
            @PathVariable String status) {
        return ResponseEntity.ok(orderService.getCustomerOrdersByStatus(customerId, status));
    }
}
