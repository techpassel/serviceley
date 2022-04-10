package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
