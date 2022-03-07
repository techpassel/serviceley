package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.CookingSpeciality;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProvidersEnrolledServiceRequestDto {
    private Long id;
    private Long serviceProviderId;
    private Long serviceTypeId;
    private Long serviceSubtypeId;
    private List<Long> suitableFrequencies;
    //Applicable only if service subtype is "cooking"
    private List<CookingSpeciality> cookingSpecialities;
}
