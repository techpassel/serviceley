package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.Coupon;
import com.tp.serviceley.server.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    List<UserCoupon> findByCoupon(Coupon coupon);
}
