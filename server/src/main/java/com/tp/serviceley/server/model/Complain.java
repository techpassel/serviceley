package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.ComplainType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "complain")
public class Complain extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_order_id", unique = true)
    private String displayComplainId;

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
    //Same for fromDate and uptoDate also these two fields will be used to mention for which dates
    //he/she have complaint in the delivery of service.
    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "upto_date")
    private LocalDate uptoDate;

    @Column(name = "final_remark")
    private String finalRemark;

    @ManyToOne
    @JoinColumn(name = "final_remark_by")
    private Staff finalRemarkBy;

    @Column(name = "customer_satisfaction_rating")
    private Integer customerSatisfactionRating;

    @Column(name = "customer_review")
    private String customerReview;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "complain")
    @Fetch(value = FetchMode.SUBSELECT)
    //In this class we have also used 'order' field which also has a 'List' type field orderItems.
    //So if we don't apply "@Fetch(value = FetchMode.SUBSELECT)" then it will throw following error -
    //org.hibernate.loader.MultipleBagFetchException: cannot simultaneously fetch multiple bags
    private List<ComplainMessage> messages;

    /*
     If a staff promise customer that he will contact him with update before some specific time
     then that specific time should be stored here. Even if we make policy like customer will be contacted
     within some specific(let's say within 2 hours) of registering complain then in such cases also we can use
     this field to store deadline time when the complaint is registered.
    */
    @Column(name = "next_response_deadline")
    private LocalDateTime nextResponseDeadline;
}
