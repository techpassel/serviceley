package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.enums.ServiceOfferingType;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSubtypeResponseDto {
    private Long id;
    private ServiceType type;
    private String subtype;
    private ServiceOfferingType serviceOfferingType;
    private List<String> relatedInformation;
    private List<String> serviceDetails;
}
