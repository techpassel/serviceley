package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.dto_related.DtoServiceFrequency;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import com.tp.serviceley.server.model.enums.CookingSpeciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvidersEnrolledServiceResponseDto {
    private Long id;
    private Long serviceProvider;
    private ServiceType serviceType;
    private DtoServiceSubtype serviceSubtype;
    private List<DtoServiceFrequency> suitableFrequencies;
    //Applicable only if service subtype is "cooking"
    private List<CookingSpeciality> cookingSpecialities;
}
