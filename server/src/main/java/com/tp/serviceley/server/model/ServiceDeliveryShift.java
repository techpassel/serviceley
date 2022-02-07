package com.tp.serviceley.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "service_delivery_shift")
public class ServiceDeliveryShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_delivery_record_id")
    private ServiceDeliveryRecord serviceDeliveryRecord;

    @Column(name = "slot_time")
    private String slotTime;
    //Example of slot time :- 12:00AM - 01.15PM, 04:30PM-06:30PM etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

    @Column(name = "is_service_delivered")
    private boolean isServiceDelivered;

    @Column(name = "service_provider_remark")
    private String serviceProviderRemark;

    @Column(name = "user_remark")
    private String userRemark;

    @Column(name = "staff_remark")
    private String staffRemark;

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
}
