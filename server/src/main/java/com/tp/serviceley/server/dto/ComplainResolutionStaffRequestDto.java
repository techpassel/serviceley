package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.Complain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplainResolutionStaffRequestDto {
    private Long id;
    private Long complainId;
    private Long staffId;
    private Long invitedByStaffId;
    private String invitationMessage;
    private boolean isInvitationAccepted;
    private String remark;
}
