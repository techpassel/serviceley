package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.PaymentType;
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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    // Here we haven't created fields for Coupon and SpecialDiscount, but we will show these values in client side.
    // But we will not do that for offers. It's because offer is meant for per service subtype.
    // So we will use that in CartItem rather than in Cart.

    @Column(name = "payment_type")
    private PaymentType paymentType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "cart")
    private List<CartItem> items;
}

