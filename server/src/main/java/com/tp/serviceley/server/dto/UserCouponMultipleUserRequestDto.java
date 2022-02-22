package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCouponMultipleUserRequestDto {
    private Long id;
    private List<Long> userIds;
    private Long couponId;
    private Integer totalAllowedUses;
    private Integer totalRemainingUses;
}
