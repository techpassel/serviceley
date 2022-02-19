package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.ServiceOfferingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "service_subtype")
public class ServiceSubtype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "service_type_id", nullable = false, referencedColumnName = "id")
    private ServiceType type;

    @NotBlank
    private String subtype;

    @Column(name = "service_offering_type")
    private ServiceOfferingType serviceOfferingType;
    // Only Kitchen Cleaning and Cloth washing subtypes will be of type Additional and
    // Only Cooking subtype will be of type MainAndAdditional.

    @Column(name = "related_information")
    @ElementCollection(targetClass = String.class)
    private List<String> relatedInformation;

    @Column(name = "service_details")
    @ElementCollection(targetClass = String.class)
    private List<String> serviceDetails;

    @Column(name = "optional_services")
    @ManyToMany(fetch = FetchType.EAGER)
    private List<ServiceSubtype> optionalServices;
    // We will store list of optional services here that can be offered with this main service
    // For example- For "Home Cleaning" main service subtype, "Cooking", "Kitchen Cleaning" and "Cloth washing"
    // can be offered as optional services and for "Cooking" main service subtype "Kitchen Cleaning" can be offered as
    // optional service.
}
