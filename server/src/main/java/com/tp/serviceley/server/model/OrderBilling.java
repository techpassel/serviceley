package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_billing")
public class OrderBilling {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "display_order_billing_id", unique = true)
    private String displayOrderBillingId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private PaymentStatus status;

    @Column(name = "for_month")
    private Integer forMonth;

    @Column(name = "for_year")
    private Integer forYear;

    @Column(name = "service_from")
    private LocalDateTime serviceFrom;

    @Column(name = "service_upto")
    private LocalDateTime serviceUpto;

    /*
        paymentDeadlineDate should be 5 days after the bill is verified by some user.
        In case of complain, if staff find any issue in billing and update the bill then paymentDeadlineDate
        should also be updated to the date which is 5 days after the date of update.
    */
    @Column(name = "payment_deadline_date")
    private LocalDate paymentDeadlineDate;

    //'estimatedAmount' or 'estimatedMonthlyAmount' whichever has some value in order table will be copied here.
    @Column(name = "estimated_amount")
    private Integer estimatedAmount;

    @Column(name = "deduction_for_leave")
    private Integer deductionForLeave;

    private Integer penalty;

    @Column(name = "coupon_discount_value")
    private Integer couponDiscountValue;

    @Column(name = "special_discount_value")
    private Integer specialDiscountValue;

    @Column(name = "total_offer_discount_value")
    private Integer totalOfferDiscountValue;

    @Column(name = "final_amount")
    private Integer finalAmount;

    /*
        Notes :-
         1.) OrderBilling will be created automatically using Cron jobs, but it needs to be verified by some staff members
         And if there is any issue in order billing then he/she should rectify that(i.e. update with correct data) and
         in that case his information should be stored in updatedBy and not in verifiedBy and he/she will also need
         to mention what was the mistake and what he rectified with details in updateRemark. And then some other senior
         staff member will need to verify the updated record and his information will be stored in verifiedBy.
         2.) Bill should be shown to user only after it is verified by some staff members.
         3.) Same should be followed in case a user complain about order billing. In case of resolving complaint the
         staff will need to update data only after getting consent from some senior staff member and how, when and from
         whom he got consent, he must store the information in updateRemark field.
    */
    @ManyToOne
    @JoinColumn(name = "verified_by")
    private User verifiedBy;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Column(name = "update_remark")
    private String updateRemark;
}
