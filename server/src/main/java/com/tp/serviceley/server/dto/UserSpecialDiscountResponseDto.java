package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSpecialDiscountResponseDto {
    private Long id;
    private DtoUser user;
    private Long SpecialDiscountId;
    private Integer totalAllowedUses;
    private Integer totalRemainingUses;
    private DtoUser issuedBy;
    private String reasonOfIssue;
    private DtoUser approvedBy;
    private String approvalRemark;
}
