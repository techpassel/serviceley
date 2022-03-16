package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoCoupon;
import com.tp.serviceley.server.model.dto_related.DtoSpecialDiscount;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDto {
    private Long id;
    private DtoUser user;
    private DtoCoupon coupon;
    private DtoSpecialDiscount specialDiscountId;
    private PaymentType paymentType;
    private List<CartItemResponseDto> items;
}
