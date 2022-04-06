package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplainResolutionStaffResponseDto {
    private Long id;
    private Long complainId;
    //We are returning complain id only so that when needed we can get the complaint details.
    private DtoStaff staff;
    private DtoStaff invitedBy;
    private String invitationMessage;
    private boolean isInvitationAccepted;
    private String remark;
}
