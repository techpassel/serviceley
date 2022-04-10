package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ComplainMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplainMessageRepository extends JpaRepository<ComplainMessage, Long> {
}
