package com.tp.serviceley.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp.serviceley.server.model.enums.OfferAmountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponRequestDto {
    private Long id;
    private Integer MinimumOrderValue;
    private Integer MinimumOrderMonth;
    private Double amount;
    private OfferAmountType amountIn;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime expiryDate;
    private Integer applicableForMonths;
    private Integer maxDiscount;
    private Boolean isApplicableForAll;
}
