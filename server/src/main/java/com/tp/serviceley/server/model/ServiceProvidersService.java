package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.CookingSpecialities;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
@Table(name = "service_providers_service")
public class ServiceProvidersService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id", nullable = false)
    private ServiceProvider serviceProvider;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_type", nullable = false)
    private ServiceType serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_subtype", nullable = false)
    private ServiceSubtype serviceSubtype;

    @NotEmpty
    @Column(name = "suitable_frequencies")
    private List<ServiceFrequency> suitableFrequencies;

    @Column(name = "cooking_specialities")
    private List<CookingSpecialities> cookingSpecialities;
    // As the name suggest it will be used for cooking service subtype only.
    // In all other cases it will be empty.
}
