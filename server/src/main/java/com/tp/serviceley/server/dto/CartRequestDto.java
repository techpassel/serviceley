package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartRequestDto {
    private Long id;
    private Long userId;
    private PaymentType paymentType;
    private List<CartItemRequestDto> items;
}
