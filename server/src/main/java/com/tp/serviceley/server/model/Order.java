package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.OrderStatus;
import com.tp.serviceley.server.model.enums.PaymentStatus;
import com.tp.serviceley.server.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_order")
//In Mysql since "order" is a reserved keyword hence we can't create table with name "order".
//Was getting problem in running program with @Table(name = "order") hence changed it to @Table(name = "user_order").
public class Order extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_order_id", unique = true)
    private String displayOrderId;

    @Column(name = "order_status")
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "payment_type")
    private PaymentType paymentType;

    // In Cart Model we have not used these fields - estimatedAmount and estimatedMonthlyAmount
    // But we will show these fields to user on cart page and when user will confirm the order we will
    // save that in order table also. We will show either estimatedAmount or estimatedMonthlyAmount based on
    // the selected PaymentType i.e. one of them will be 0.
    @Column(name = "estimated_amount")
    private Integer estimatedAmount;

    @Column(name = "estimated_monthly_amount")
    private Integer estimatedMonthlyAmount;

    @Column(name = "deposit_amount")
    private Integer depositAmount;

    @Column(name = "deposit_status")
    private PaymentStatus depositStatus;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "coupon_remaining_months")
    private Integer couponRemainingMonths;

    @ManyToOne
    @JoinColumn(name = "special_discount_id")
    private SpecialDiscount specialDiscount;

    @Column(name = "special_discount_remaining_months")
    private Integer specialDiscountRemainingMonths;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items;

    @Column(name = "approved_on")
    private LocalDateTime approvedOn;

    @Column(name = "activated_on")
    private LocalDateTime activatedOn;

    @Column(name = "cancellation_requested_on")
    private LocalDateTime cancellationRequestedOn;

    @Column(name = "cancelled_on")
    private LocalDateTime cancelledOn;
    // Only staff can cancel an order.He/she can do so only in case user requested for it or in case of some kind of issue
    // between company and user like user is not paying for services or user misbehaved with service provider or user
    // is not following terms and conditions. In case order is cancelled due to any issue between company and user,
    // the issue must be stored in details in cancellationRemark. In case user have requested for it then cancellation
    // remark is optional and user can also send details that why he/she is cancelling order in cancellationRequestRemark.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelled_by")
    private User cancelledBy;

    @Column(columnDefinition = "TEXT", name = "cancellation_request_remark")
    private String cancellationRequestRemark;

    @Column(columnDefinition = "TEXT", name = "cancellation_remark")
    private String cancellationRemark;
}
