package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.OrderStatus;
import com.tp.serviceley.server.model.enums.PaymentStatus;
import com.tp.serviceley.server.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    @Column(name = "service_active_on")
    private LocalDateTime serviceActiveOn;
}
