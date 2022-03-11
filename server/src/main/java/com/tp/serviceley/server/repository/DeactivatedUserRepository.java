package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.DeactivatedUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeactivatedUserRepository extends JpaRepository<DeactivatedUser, Long> {
}
