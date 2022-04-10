package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.Complain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainRepository extends JpaRepository<Complain, Long> {
}
