package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.ServiceUnitMeasure;
import com.tp.serviceley.server.model.enums.ServiceUnitType;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceUnitResponseDto {
    private Long id;
    private DtoServiceSubtype serviceSubtype;
    private ServiceUnitType serviceUnitType;
    private Long unitLimit;
    private ServiceUnitMeasure serviceUnitMeasure;
}
