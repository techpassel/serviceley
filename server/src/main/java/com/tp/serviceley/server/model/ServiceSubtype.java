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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "service_type_id", nullable = false, referencedColumnName = "id")
    private ServiceType type;

    @NotBlank
    private String subtype;

    @Column(name = "service_offering_type", nullable = false)
    private ServiceOfferingType serviceOfferingType;

    @Column(name = "related_information")
    @ElementCollection(targetClass = String.class)
    private List<String> relatedInformation;

    @Column(name = "service_details")
    @ElementCollection(targetClass = String.class)
    private List<String> serviceDetails;
}
