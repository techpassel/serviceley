package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
}
