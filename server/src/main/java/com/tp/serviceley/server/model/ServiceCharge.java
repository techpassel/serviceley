package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.BillingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_charge")
public class ServiceCharge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_subtype_id", nullable = false)
    private ServiceSubtype serviceSubtype;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_unit_id")
    private ServiceUnit serviceUnit;

    @NotNull
    private BillingType billingType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_frequency_id", nullable = false)
    private ServiceFrequency serviceFrequency;

    @NotNull
    private Double charge;

    @Column(name = "charge_as_additional_service")
    private Double chargeAsAdditionalService;
}
