package com.tp.serviceley.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceDeliveryRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    private LocalDate date;

    @Column(name = "slot_time")
    private String slotTime;
    //Example of slot time :- 12:00AM - 01.15PM, 04:30PM - 06:30PM etc.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

    @Column(name = "is_service_delivered")
    private boolean isServiceDelivered;

    @Column(name = "service_provider_remark")
    private String serviceProviderRemark;

    @Column(name = "customer_remark")
    private String customerRemark;

    @Column(name = "customer_satisfaction_rating")
    private Integer customerSatisfactionRating;

    @Column(name = "staff_remark")
    private String staffRemark;

    @Column(name = "is_marked_as_ignored")
    private boolean isMarkedAsIgnored;
    /*
     Suppose customer have some issue with this slot delivery, like he is completely unsatisfied with
     the provided service. So he complained about it and after listening him staff finds his concern as
     genuine, and he wants to mark this delivery as ignored so that customer doesn't get charged for it.
     We can use this field for this purpose.
    */

    @ManyToOne
    @JoinColumn(name = "staff_id")
    private Staff staff;
}
