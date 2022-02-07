package com.tp.serviceley.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cart")
public class Cart extends CreateUpdateRecord{
    // Unlike other apps where one user have only one cart, in our app one user can have multiple carts.
    // In fact for every "Main" ServiceOfferingType we will create a separate cart.
    // And for every cart we will create a separate order. Every Cart will have its own offer, coupon etc.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "offer_id")
    private Offer offer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "special_discount_id")
    private SpecialDiscount specialDiscount;

    @Column(name = "service_from_date")
    private LocalDate serviceFromDate;

    @Column(name = "service_upto_date")
    private LocalDate serviceUptoDate;
    //serviceUpto date can be maximum 12 months after the serviceFromDate.

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cart")
    private List<CartItem> items;
}

