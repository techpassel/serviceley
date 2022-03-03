package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.ServiceProvider;
import com.tp.serviceley.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProviderRepository extends JpaRepository<ServiceProvider, Long> {
    ServiceProvider findByUser(User user);
}
