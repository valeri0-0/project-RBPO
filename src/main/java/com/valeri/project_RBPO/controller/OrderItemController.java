package com.valeri.project_RBPO.controller;

import com.valeri.project_RBPO.entity.OrderItem;
import com.valeri.project_RBPO.model.OrderItemDto;
import com.valeri.project_RBPO.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class OrderItemController {

    private final OrderItemService orderItemService;

    // Получить все позиции заказа
    @GetMapping
    public ResponseEntity<List<OrderItem>> getAllOrderItems() {
        return ResponseEntity.ok(orderItemService.getAllOrderItems());
    }

    // Получить позицию заказа по ID
    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> getOrderItemById(@PathVariable UUID id) {
        OrderItem orderItem = orderItemService.getOrderItemById(id);
        return orderItem != null ? ResponseEntity.ok(orderItem) : ResponseEntity.notFound().build();
    }

    // Создать новую позицию заказа
    @PostMapping
    public ResponseEntity<?> createOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {
        try {
            OrderItem orderItem = orderItemService.createOrderItem(orderItemDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(orderItem);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Обновить позицию заказа
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderItem(@Valid @PathVariable UUID id, @RequestBody OrderItemDto orderItemDto) {
        try {
            OrderItem orderItem = orderItemService.updateOrderItem(id, orderItemDto);
            return orderItem != null ? ResponseEntity.ok(orderItem) : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Удалить позицию заказа
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable UUID id) {
        return orderItemService.deleteOrderItem(id) ?
                ResponseEntity.noContent().build() :
                ResponseEntity.notFound().build();
    }

    // Получить позиции заказа по ID заказа
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<OrderItem>> getOrderItemsByOrderId(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderItemService.getOrderItemsByOrderId(orderId));
    }
}