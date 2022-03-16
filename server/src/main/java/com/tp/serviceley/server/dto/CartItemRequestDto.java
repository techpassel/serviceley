package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequestDto {
    private Long id;
    private Long cartId;
    private Long serviceTypeId;
    private Long serviceSubtypeId;
    private Long offerId;
    private Long serviceUnitId;
    private Long serviceFrequency;
    private LocalDate serviceFromDate;
    private LocalDate serviceUptoDate;
}
