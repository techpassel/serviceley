package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {
}
