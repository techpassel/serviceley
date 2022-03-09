package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.StaffRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaffRequestDto {
    private Long id;
    private Long userId;
    private StaffRole staffRole;
}
