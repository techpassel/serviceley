package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.ServiceUnitMeasure;
import com.tp.serviceley.server.model.enums.ServiceUnitType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUnitRequestDto {
    private Long id;
    private Long serviceSubtypeId;
    private ServiceUnitType serviceUnitType;
    private Long unitLimit;
    private ServiceUnitMeasure serviceUnitMeasure;
}
