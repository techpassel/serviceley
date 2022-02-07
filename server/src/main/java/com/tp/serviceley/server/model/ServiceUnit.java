package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.ServiceUnitMeasure;
import com.tp.serviceley.server.model.enums.ServiceUnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_unit")
public class ServiceUnit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_subtype_id", nullable = false)
    private ServiceSubtype serviceSubtype;

    @NotNull
    @Column(name = "service_unit_type", nullable = false)
    private ServiceUnitType serviceUnitType;

    @NotNull
    @Column(name = "unit_limit")
    private Long unitLimit;

    @NotNull
    @Column(name = "service_unit_measure", nullable = false)
    private ServiceUnitMeasure serviceUnitMeasure;
}
