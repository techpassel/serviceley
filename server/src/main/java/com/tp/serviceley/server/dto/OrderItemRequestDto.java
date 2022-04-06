package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {
    private Long id;
    private Long orderId;
    private Long serviceTypeId;
    private Long serviceSubtypeId;
    private Long serviceUnitId;
    private Long serviceFrequencyId;
    private Long offerId;
    private Long OfferDiscount;
    private LocalDateTime serviceFrom;
    private LocalDateTime serviceUpto;
    private Long assignedServiceProviderId;
    private Long assignedStaffId;
    private String slotTime1;
    private String slotTime2;
    private String slotTime3;
    private String slotTime4;
    private String slotTime5;
}
