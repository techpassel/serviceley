package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.OfferAmountType;
import com.tp.serviceley.server.model.enums.SpecialDiscountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "special_discount")
public class SpecialDiscount extends CreateUpdateRecord{
    //Whom this special discount is offered we will store that information in UserCoupon table.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 16, max = 16)
    @Column(length = 16)
    private String code;

    private Double amount;

    @Column(name = "amount_in")
    private OfferAmountType amountIn;
    // Offer can be in rupees, dollar or percentage
    // So, for 5% discount we will store amount as 5 and amountIn as Percentage

    @Column(name = "minimum_order_value")
    private Integer MinimumOrderValue;

    @Column(name = "minimum_order_month")
    private Integer MinimumOrderMonth;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "applicable_for_months")
    private Integer applicableForMonths;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    // We can use '@Lob' also in place of '@Column(columnDefinition = "TEXT")' but that will create LongText type
    // filed, but we want only "Text" type field as that will be enough for our requirement.
    @Column(columnDefinition = "TEXT", name = "remark")
    private String remark;
}
