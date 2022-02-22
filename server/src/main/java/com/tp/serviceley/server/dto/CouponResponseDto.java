package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.OfferAmountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponseDto {
    private Long id;
    private Integer MinimumOrderValue;
    private Integer MinimumOrderMonth;
    private Double amount;
    private OfferAmountType amountIn;
    private LocalDateTime expiryDate;
    private Integer applicableForMonths;
    private Integer maxDiscount;
    private Boolean isApplicableForAll;
    private DtoUser createdBy;
    private DtoUser updatedBy;
}
