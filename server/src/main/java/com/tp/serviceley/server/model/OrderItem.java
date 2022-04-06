package com.tp.serviceley.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_item")
public class OrderItem{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "service_type_id")
    private ServiceType serviceType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_subtype_id")
    private ServiceSubtype serviceSubtype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_unit_id")
    private ServiceUnit serviceUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_frequency_id")
    private ServiceFrequency serviceFrequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @Column(name = "offer_discount")
    private Long OfferDiscount;

    @Column(name = "service_from")
    private LocalDateTime serviceFrom;

    @Column(name = "service_upto")
    private LocalDateTime serviceUpto;
    // serviceUpto date can be maximum 12 months after the serviceFromDate.

    // Staff will be assigned when order will be approved by some senior staff and then the responsibility of
    // assigning ServiceProvider setting shiftTimings and ensuring service delivery will lie with that staff.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_staff_id")
    private Staff assignedStaff;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider assignedServiceProvider;

    @Column(name = "slot_time1")
    private String slotTime1;

    @Column(name = "slot_time2")
    private String slotTime2;

    @Column(name = "slot_time3")
    private String slotTime3;

    @Column(name = "slot_time4")
    private String slotTime4;

    @Column(name = "slot_time5")
    private String slotTime5;
}
