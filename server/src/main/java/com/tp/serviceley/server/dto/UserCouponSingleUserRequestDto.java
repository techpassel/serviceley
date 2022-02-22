package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponSingleUserRequestDto {
    private Long id;
    private Long userId;
    private Long couponId;
    private Integer totalAllowedUses;
}
