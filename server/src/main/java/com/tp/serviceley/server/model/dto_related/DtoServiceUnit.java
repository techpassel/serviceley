package com.tp.serviceley.server.model.dto_related;

import com.tp.serviceley.server.model.enums.ServiceUnitMeasure;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoServiceUnit {
    private Long id;
    private Long unitLimit;
    private ServiceUnitMeasure serviceUnitMeasure;
}
