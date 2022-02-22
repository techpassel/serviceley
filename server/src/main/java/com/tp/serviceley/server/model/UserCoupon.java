package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.SpecialDiscountStatus;
import com.tp.serviceley.server.model.enums.UserCouponType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_coupon")
public class UserCoupon extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "total_allowed_uses")
    private Integer totalAllowedUses;

    @Column(name = "total_remaining_uses")
    private Integer totalRemainingUses;
}
