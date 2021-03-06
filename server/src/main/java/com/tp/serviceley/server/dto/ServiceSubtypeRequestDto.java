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
}
