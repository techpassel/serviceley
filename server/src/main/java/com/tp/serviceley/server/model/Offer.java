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
@Table(name = "offer")
public class Offer extends CreateUpdateRecord{
    /*
     1.) Offer will be created for per-service basis. For offer on order amount use Coupon instead.
     2.) Offer will be used to calculate estimated amount while Coupon and SpecialDiscount will not be
     considered in calculation of estimated amount
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "service_type_id", nullable = false)
    private ServiceType serviceType;

    @ManyToOne
    @JoinColumn(name = "service_subtype_id")
    private ServiceSubtype serviceSubtype;

    @Column(name = "minimum_order_value")
    private Integer minimumOrderValue = -1;

    @Column(name = "minimum_order_month")
    private Integer minimumOrderMonth = -1;

    private Double amount;

    @Column(name = "amount_in")
    private OfferAmountType amountIn;
    // Offer can be in rupees, dollar or percentage
    // So, for 5% discount we will store amount as 5 and amountIn as Percentage

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false, updatable = false)
    private User createdBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;
}
