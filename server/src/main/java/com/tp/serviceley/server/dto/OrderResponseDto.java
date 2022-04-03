package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoCoupon;
import com.tp.serviceley.server.model.dto_related.DtoSpecialDiscount;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.OrderStatus;
import com.tp.serviceley.server.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private OrderStatus status;
    private DtoUser user;
    private Integer estimatedAmount;
    private Integer estimatedMonthlyAmount;
    private Integer depositAmount;
    private PaymentStatus depositStatus;
    private DtoCoupon coupon;
    private Integer couponRemainingMonths;
    private DtoSpecialDiscount specialDiscount;
    private Integer specialDiscountRemainingMonths;
    private List<OrderItemResponseDto> items;
    private LocalDateTime approvedOn;
    private LocalDateTime activatedOn;
    private LocalDateTime cancellationRequestedOn;
    private LocalDateTime cancelledOn;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
