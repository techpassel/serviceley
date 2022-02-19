package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ServiceUnitRequestDto;
import com.tp.serviceley.server.dto.ServiceUnitResponseDto;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceUnit;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ServiceUnitMapper {

    @Mapping(target = "serviceSubtype", source = "serviceSubtype")
    @Mapping(target = "id", source = "serviceUnitRequestDto.id")
    public abstract ServiceUnit mapToModel(ServiceUnitRequestDto serviceUnitRequestDto, ServiceSubtype serviceSubtype);

    @Mapping(target = "serviceSubtype", expression = "java(getDtoServiceSubtype(serviceUnit))")
    public abstract ServiceUnitResponseDto mapToDto(ServiceUnit serviceUnit);

    DtoServiceSubtype getDtoServiceSubtype(ServiceUnit serviceUnit){
        ServiceSubtype serviceSubtype = serviceUnit.getServiceSubtype();
        return new DtoServiceSubtype(serviceSubtype.getId(), serviceSubtype.getSubtype());
    }
}
