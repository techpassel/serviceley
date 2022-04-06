package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoOrder;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderBillingResponseDto {
    private Long id;
    private String displayOrderBillingId;
    private DtoOrder order;
    private PaymentStatus status;
    private Integer forMonth;
    private Integer forYear;
    private LocalDateTime serviceFrom;
    private LocalDateTime serviceUpto;
    private LocalDate paymentDeadlineDate;
    private Integer estimatedAmount;
    private Integer deductionForLeave;
    private Integer penalty;
    private Integer couponDiscountValue;
    private Integer specialDiscountValue;
    private Integer totalOfferDiscountValue;
    private DtoUser verifiedBy;
    private DtoUser updatedBy;
    private String updateRemark;
}
