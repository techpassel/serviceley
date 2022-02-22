package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
