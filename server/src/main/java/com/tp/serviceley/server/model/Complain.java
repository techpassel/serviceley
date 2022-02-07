package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.ComplainType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complain")
public class Complain extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "complain_type")
    private ComplainType complainType;

    //Will be used if ComplainType is ServiceProviderRelated otherwise null;
    @ManyToOne
    @JoinColumn(name = "service_provider_id")
    private ServiceProvider serviceProvider;

    //Will be used if complain type is - OrderRelated, PaymentRelated or ServiceDeliveryRelated(Common for all three)
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    //Will be used if ComplainType is PaymentRelated otherwise null;
    @ManyToOne
    @JoinColumn(name = "order_billing_id")
    private OrderBilling orderBilling;

    //Will be used if ComplainType is ServiceDeliveryRelated otherwise null;
    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "upto_date")
    private LocalDate uptoDate;

    private String remark;

    @ManyToOne
    @JoinColumn(name = "remark_by_userid")
    private User remarkBy;

    @Column(name = "customer_satisfaction_level")
    private Integer customerSatisfactionLevel;

    @Column(name = "customerReview")
    private String customerReview;
}
