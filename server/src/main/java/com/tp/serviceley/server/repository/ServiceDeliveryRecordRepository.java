package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ServiceDeliveryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceDeliveryRecordRepository extends JpaRepository<ServiceDeliveryRecord, Long> {

}
