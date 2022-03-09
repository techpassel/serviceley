package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffResponseDto {
    private Long id;
    private DtoUser user;
    private StaffRole staffRole;
}
