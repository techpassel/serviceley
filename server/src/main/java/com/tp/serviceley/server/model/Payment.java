package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "payment")
public class Payment extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "is_deposit_payment")
    private Boolean isDepositPayment;
    //It will be true for deposit payment and false for orderBilling;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_billing_id")
    private OrderBilling orderBilling;
    //It will be null in case of deposit payment

    private PaymentStatus status;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_provider")
    private String paymentProvider;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(columnDefinition = "TEXT", name = "payment_details")
    private String paymentDetails;
}
