package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ServiceSubtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ServiceSubtypeRepository extends JpaRepository<ServiceSubtype, Long> {
    @Query("from ServiceSubtype where service_type_id=?1 and subtype=?2")
    Optional<ServiceSubtype> findByTypeAndSubtype(Long typeId, String subtype);
}
