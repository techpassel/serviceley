package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ServiceFrequencyRequestDto;
import com.tp.serviceley.server.dto.ServiceFrequencyResponseDto;
import com.tp.serviceley.server.model.ServiceFrequency;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ServiceFrequencyMapper {

    @Mapping(target = "serviceSubtype", source = "serviceSubtype")
    @Mapping(target = "id", source = "serviceFrequencyRequestDto.id")
    public abstract ServiceFrequency mapToModel(ServiceFrequencyRequestDto serviceFrequencyRequestDto,
                                                ServiceSubtype serviceSubtype);

    @Mapping(target = "serviceSubtype", expression = "java(getDtoServiceSubtype(serviceFrequency))")
    public abstract ServiceFrequencyResponseDto mapToDto(ServiceFrequency serviceFrequency);

    DtoServiceSubtype getDtoServiceSubtype(ServiceFrequency serviceFrequency){
        ServiceSubtype serviceSubtype = serviceFrequency.getServiceSubtype();
        return new DtoServiceSubtype(serviceSubtype.getId(), serviceSubtype.getSubtype());
    }
}
