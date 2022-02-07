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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Size(min = 16, max = 16)
    @Column(length = 16)
    private String code;

    private Double amount;

    @Column(name = "amount_in")
    private OfferAmountType amountIn;
    // Offer can be in rupees, dollar or percentage
    // So, for 5% discount we will store amount as 5 and amountIn as Percentage

    @Column(name = "minimum_order_value")
    private Integer MinimumOrderValue = -1;
    // Here -1 is default value which means no MinimumOrderValue is required.

    @Column(name = "minimum_order_month")
    private Integer MinimumOrderMonth = -1;
    // Here -1 is default value which means no MinimumOrderMonth is required.

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    @Column(name = "applicable_for_months")
    private int applicableForMonths = -1;
    // Here -1 is default value which means discount will be applicable as long as order is active.

    @ManyToOne
    @JoinColumn(name = "issued_by")
    private User issuedBy;

    // We can use '@Lob' also in place of '@Column(columnDefinition = "TEXT")' but that will create LongText type
    // filed, but we want only "Text" type field as that will be enough for our requirement.
    @Column(columnDefinition = "TEXT", name = "reason_of_issue")
    private String reasonOfIssue;

    private SpecialDiscountStatus status;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;
    // Here we have given the name of field as "approvedBy" but in case of rejection also
    // we will store the userId of the user who reject this in this field.

    @Column(columnDefinition = "TEXT", name = "approval_remark")
    private String approvalRemark;
    // Not only approval remark but rejection remark should also be stored in this field.
}
