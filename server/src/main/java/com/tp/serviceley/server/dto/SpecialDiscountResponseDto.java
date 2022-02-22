package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.OfferAmountType;
import com.tp.serviceley.server.model.enums.SpecialDiscountStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialDiscountResponseDto {
    private Long id;
    private String code;
    private Double amount;
    private OfferAmountType amountIn;
    private Integer MinimumOrderValue;
    private Integer MinimumOrderMonth;
    private LocalDateTime expiryDate;
    private Integer applicableForMonths;
    private SpecialDiscountStatus status;
    private DtoUser createdBy;
    private String remark;
}
