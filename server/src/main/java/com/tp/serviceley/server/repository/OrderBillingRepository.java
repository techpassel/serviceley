package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.OrderBilling;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBillingRepository extends JpaRepository<OrderBilling, Long> {
}
