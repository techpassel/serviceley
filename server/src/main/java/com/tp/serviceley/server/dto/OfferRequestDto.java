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
public class OfferRequestDto {
    private Long id;
    private Long serviceTypeId;
    private Long serviceSubtypeId;
    private Integer minimumOrderValue;
    private Integer minimumOrderMonth;
    private Double amount;
    private OfferAmountType amountIn;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy'T'HH:mm:ss")
    private LocalDateTime expiryDate;
}
