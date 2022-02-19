package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ServiceFrequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ServiceFrequencyRepository extends JpaRepository<ServiceFrequency, Long> {
    @Query("from ServiceFrequency where service_subtype_id=?1 and frequency=?2")
    List<ServiceFrequency> findByServiceSubtypeAndFrequency(Long serviceSubtypeId, String frequency);
}
