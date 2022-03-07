package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.CookingSpeciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "providers_enrolled_service")
public class ProvidersEnrolledService {
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

    //Unidirectional Many-to-many relationship
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(name = "providers_enrolled_service_frequencies",
            joinColumns = { @JoinColumn(name = "providers_enrolled_service_id") },
            inverseJoinColumns = { @JoinColumn(name = "service_frequency_id") })
    private List<ServiceFrequency> suitableFrequencies;

    @ElementCollection(targetClass = CookingSpeciality.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private List<CookingSpeciality> cookingSpecialities;
}
