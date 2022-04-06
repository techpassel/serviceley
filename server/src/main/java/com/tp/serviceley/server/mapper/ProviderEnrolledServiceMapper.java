package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ProvidersEnrolledServiceRequestDto;
import com.tp.serviceley.server.dto.ProvidersEnrolledServiceResponseDto;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.dto_related.DtoServiceFrequency;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ProviderEnrolledServiceMapper {
    @Mapping(target = "id", source = "providersEnrolledServiceRequestDto.id")
    @Mapping(target = "serviceProvider", source = "serviceProvider")
    @Mapping(target = "serviceType", source = "serviceType")
    @Mapping(target = "serviceSubtype", source = "serviceSubtype")
    @Mapping(target = "suitableFrequencies", source = "suitableFrequencies")
    public abstract ProvidersEnrolledService mapToModel(
            ProvidersEnrolledServiceRequestDto providersEnrolledServiceRequestDto, ServiceProvider serviceProvider,
            ServiceType serviceType, ServiceSubtype serviceSubtype, List<ServiceFrequency> suitableFrequencies);

    @Mapping(target = "serviceProvider", expression = "java(getServiceProviderId(providersEnrolledService))")
    @Mapping(target = "serviceSubtype", expression = "java(getDtoServiceSubtype(providersEnrolledService))")
    @Mapping(target = "suitableFrequencies", expression = "java(getDtoServiceFrequencies(providersEnrolledService))")
    public abstract ProvidersEnrolledServiceResponseDto mapToDto(ProvidersEnrolledService providersEnrolledService);

    public Long getServiceProviderId(ProvidersEnrolledService providersEnrolledService){
        return providersEnrolledService.getServiceProvider().getId();
    }

    public DtoServiceSubtype getDtoServiceSubtype(ProvidersEnrolledService providersEnrolledService){
        return new DtoServiceSubtype(providersEnrolledService.getServiceSubtype().getId(),
                providersEnrolledService.getServiceSubtype().getSubtype());
    }

    public List<DtoServiceFrequency> getDtoServiceFrequencies(ProvidersEnrolledService providersEnrolledService){
        return providersEnrolledService.getSuitableFrequencies().stream().map(d -> new DtoServiceFrequency
                (d.getId(), d.getFrequency(), d.getFrequencyDetails())).collect(Collectors.toList());
    }

}
