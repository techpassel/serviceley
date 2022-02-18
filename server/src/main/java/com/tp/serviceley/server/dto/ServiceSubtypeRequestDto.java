package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.ServiceOfferingType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceSubtypeRequestDto {
    private Long id;
    private Long typeId;
    private String subtype;
    private ServiceOfferingType serviceOfferingType;
    private List<String> relatedInformation;
    private List<String> serviceDetails;
    private List<Long> optionalServices;

    ServiceSubtypeRequestDto(Long typeId, String subtype, ServiceOfferingType serviceOfferingType, List<String> relatedInformation, List<String> serviceDetails, List<Long> optionalServices){
        this.typeId = typeId;
        this.subtype = subtype;
        this.serviceOfferingType = serviceOfferingType;
        this.relatedInformation = relatedInformation;
        this.serviceDetails = serviceDetails;
        this.optionalServices = optionalServices;
    }
}
