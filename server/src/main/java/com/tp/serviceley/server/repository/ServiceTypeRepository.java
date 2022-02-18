package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {
    Optional<ServiceType> findByType(String type);
}
