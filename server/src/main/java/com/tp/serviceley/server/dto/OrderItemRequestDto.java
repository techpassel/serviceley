package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate serviceFromDate;
    private LocalDate serviceUptoDate;
    private Long assignedServiceProvider;
    private Long assignedStaff;
    private String shiftTiming1;
    private String shiftTiming2;
    private String shiftTiming3;
}
