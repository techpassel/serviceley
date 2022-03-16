package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.OrderStatus;
import com.tp.serviceley.server.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private Long id;
    private OrderStatus status;
    private Long userId;
    private Integer estimatedAmount;
    private Integer estimatedMonthlyAmount;
    private Integer depositAmount;
    private PaymentStatus depositStatus;
    private Long couponId;
    private Integer couponRemainingMonths;
    private Long specialDiscountId;
    private Integer specialDiscountRemainingMonths;
    private List<OrderItemRequestDto> items;
}
