package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.OfferAmountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "coupon")
public class Coupon extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    @Column(name = "minimum_order_value", nullable = false)
    private Integer MinimumOrderValue = -1;

    @Column(name = "minimum_order_month", nullable = false)
    private Integer MinimumOrderMonth = -1;

    @Column(nullable = false)
    private Integer amount;

    @Column(name = "amount_in", nullable = false)
    private OfferAmountType amountIn;
    // Offer can be in rupees or percentage
    // So, for 5% discount we will store amount as 5 and amountIn as Percentage

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @Column(name = "applicable_for_months")
    private Integer applicableForMonths = -1;
    // Here -1 is default value which means discount will be applicable as long as order is active.

    @Column(name = "max_discount")
    private Integer maxDiscount = -1;
    // maxDiscount will always be in rupee and will be applicable only if 'amountIn' is selected as
    // percentage and not rupee. Here -1 is the default value which basically means no limit on discount value.

    @Column(name = "is_applicable_for_all")
    private Boolean isApplicableForAll;
    // If isApplicableForAll is true then it wil be applicable for all users otherwise
    // An entry should be created in UserCoupon table for every user whom this coupon is assigned.

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}
