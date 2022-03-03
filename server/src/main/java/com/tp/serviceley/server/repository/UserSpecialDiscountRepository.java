package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.SpecialDiscount;
import com.tp.serviceley.server.model.UserSpecialDiscount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserSpecialDiscountRepository extends JpaRepository<UserSpecialDiscount, Long> {
    List<UserSpecialDiscount> findBySpecialDiscount(SpecialDiscount specialDiscount);
}
