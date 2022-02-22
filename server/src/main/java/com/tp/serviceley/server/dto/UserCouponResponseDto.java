package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponResponseDto {
    private Long id;
    private DtoUser user;
    private Long couponId;
    private Integer totalAllowedUses;
    private Integer totalRemainingUses;
}
