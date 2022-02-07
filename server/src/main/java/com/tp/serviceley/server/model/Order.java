package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.OrderStatus;
import com.tp.serviceley.server.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    //Check Cart model for details of estimatedAmount and estimatedMonthlyAmount
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
    private Integer couponMonths;

    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "special_discount_id")
    private SpecialDiscount specialDiscount;

    @Column(name = "special_discount_remaining_months")
    private Integer specialDiscountMonths;

    @Column(name = "service_from_date")
    private LocalDate serviceFromDate;

    @Column(name = "service_upto_date")
    private LocalDate serviceUptoDate;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "order")
    private List<OrderItem> items;
}
