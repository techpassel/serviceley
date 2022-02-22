package com.tp.serviceley.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.enums.OfferAmountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecialDiscountRequestDto {
    private Long id;
    private String code;
    private Double amount;
    private OfferAmountType amountIn;
    private Integer MinimumOrderValue;
    private Integer MinimumOrderMonth;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime expiryDate;
    private Integer applicableForMonths;
    private String remark;
}
