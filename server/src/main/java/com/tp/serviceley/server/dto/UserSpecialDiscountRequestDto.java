package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecialDiscountRequestDto{
    private Long id;
    private List<Long> userIds;
    private Long specialDiscountId;
    private Integer totalAllowedUses;
    private Integer totalRemainingUses;
    private String reasonOfIssue;
}
