package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ServiceUnit;
import com.tp.serviceley.server.model.enums.ServiceUnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceUnitRepository extends JpaRepository<ServiceUnit, Long> {
    @Query("from ServiceUnit where service_subtype_id=?1 and service_unit_type=?2 and unit_limit=?3")
    List<ServiceUnit> findBySubtypeUnitAndLimit(Long subtypeId, int serviceUnitType, Long unitLimit);
    @Query("from ServiceUnit where service_subtype_id=?1")
    List<ServiceUnit> findBySubtypeId(Long subtypeId);
    @Query("from ServiceUnit where service_unit_type=?1")
    List<ServiceUnit> findByServiceUnitType(int serviceUnitType);
    @Query("from ServiceUnit where unit_limit=?1")
    List<ServiceUnit> findByUnitLimit(Long unitLimit);
}
