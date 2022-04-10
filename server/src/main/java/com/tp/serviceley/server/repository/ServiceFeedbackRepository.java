package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ServiceFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceFeedbackRepository extends JpaRepository<ServiceFeedback, Long> {
}
