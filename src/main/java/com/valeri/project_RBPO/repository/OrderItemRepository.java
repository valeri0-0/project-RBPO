package com.valeri.project_RBPO.repository;

import com.valeri.project_RBPO.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.Modifying;

@Repository
public interface OrderItemRepository
        extends JpaRepository <OrderItem, UUID> {
    List<OrderItem> findByOrderId(UUID orderId);

    @Query("SELECT oi.product, SUM(oi.quantity) as totalQuantity, SUM(oi.quantity * oi.price) as totalRevenue FROM OrderItem oi GROUP BY oi.product ORDER BY totalQuantity DESC")
    List<Object[]> findPopularProducts();

    @Query("SELECT SUM(oi.quantity * oi.price) FROM OrderItem oi WHERE oi.order.id = :orderId")
    Double calculateOrderTotal(UUID orderId);

    @Modifying
    @Query(value = "DELETE FROM order_items WHERE id = ?1", nativeQuery = true)
    int deleteByIdNative(UUID id);
}
