package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.SpecialDiscountStatus;

import javax.persistence.*;

public class UserSpecialDiscount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private SpecialDiscountStatus status = SpecialDiscountStatus.Created;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "special_discount_id")
    private SpecialDiscount specialDiscount;

    @Column(name = "total_allowed_uses")
    private Integer totalAllowedUses;

    @Column(name = "total_remaining_uses")
    private Integer totalRemainingUses;

    @ManyToOne
    @JoinColumn(name = "issued_by")
    private User issuedBy;

    // We can use '@Lob' also in place of '@Column(columnDefinition = "TEXT")' but that will create LongText type
    // filed, but we want only "Text" type field as that will be enough for our requirement.
    @Column(columnDefinition = "TEXT", name = "reason_of_issue")
    private String reasonOfIssue;

    @ManyToOne
    @JoinColumn(name = "approved_by")
    private User approvedBy;
    // Here we have given the name of field as "approvedBy" but in case of rejection also
    // we will store the userId of the user who reject this in this field.

    @Column(columnDefinition = "TEXT", name = "approval_remark")
    private String approvalRemark;
    // Not only approval remark but rejection remark should also be stored in this field.
}
