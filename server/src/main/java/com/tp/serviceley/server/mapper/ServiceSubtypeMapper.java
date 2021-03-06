package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ServiceSubtypeRequestDto;
import com.tp.serviceley.server.dto.ServiceSubtypeResponseDto;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ServiceSubtypeMapper {
    @Mapping(target = "type", source = "serviceType")
    @Mapping(target = "id", source = "serviceSubtypeRequestDto.id")
    public abstract ServiceSubtype mapToModel(ServiceSubtypeRequestDto serviceSubtypeRequestDto, ServiceType serviceType);

    public abstract ServiceSubtypeResponseDto mapToDto(ServiceSubtype serviceSubtype);
}
