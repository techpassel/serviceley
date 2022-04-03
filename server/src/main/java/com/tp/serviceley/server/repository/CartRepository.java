package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.Cart;
import com.tp.serviceley.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}