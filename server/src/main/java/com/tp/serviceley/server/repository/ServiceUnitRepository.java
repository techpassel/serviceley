package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceUnit;
import com.tp.serviceley.server.model.enums.ServiceUnitType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ServiceUnitRepository extends JpaRepository<ServiceUnit, Long> {
    @Query("from ServiceUnit where service_subtype_id=?1 and service_unit_type=?2 and unit_limit=?3")
    List<ServiceUnit> findBySubtypeUnitAndLimit(Long subtypeId, int serviceUnitType, Long unitLimit);

    List<ServiceUnit> findByServiceSubtype(ServiceSubtype serviceSubtype);
}
