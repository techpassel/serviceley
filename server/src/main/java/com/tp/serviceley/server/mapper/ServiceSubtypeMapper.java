package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ServiceSubtypeRequestDto;
import com.tp.serviceley.server.dto.ServiceSubtypeResponseDto;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ServiceSubtypeMapper {
    @Mapping(target = "type", source = "serviceType")
    @Mapping(target = "optionalServices", source = "optionalServices")
    @Mapping(target = "id", source = "serviceSubtypeRequestDto.id")
    public abstract ServiceSubtype mapToModel(ServiceSubtypeRequestDto serviceSubtypeRequestDto, ServiceType serviceType,
                                              List<ServiceSubtype> optionalServices);

    @Mapping(target = "optionalServices", expression = "java(getOptionalServices(serviceSubtype))")
    public abstract ServiceSubtypeResponseDto mapToDto(ServiceSubtype serviceSubtype);

    List<DtoServiceSubtype> getOptionalServices(ServiceSubtype serviceSubtype){
        return serviceSubtype.getOptionalServices().stream().map(e -> new DtoServiceSubtype(e.getId(), e.getSubtype())).
        collect(Collectors.toList());
    }
}
