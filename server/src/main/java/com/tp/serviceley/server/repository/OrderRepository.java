package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
