package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.OfferAmountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OfferResponseDto {
    private Long id;
    private ServiceType serviceType;
    private DtoServiceSubtype serviceSubtype;
    private Integer minimumOrderValue;
    private Integer minimumOrderMonth;
    private Double amount;
    private OfferAmountType amountIn;
    private LocalDateTime expiryDate;
    private DtoUser createdBy;
    private DtoUser updatedBy;
}
