package com.tp.serviceley.server.model.dto_related;

import com.tp.serviceley.server.model.enums.OfferAmountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoOffer {
    private Long id;
    private Integer amount;
    private OfferAmountType amountIn;
}
