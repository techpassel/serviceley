package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ServiceProviderRequestDto;
import com.tp.serviceley.server.dto.ServiceProviderResponseDto;
import com.tp.serviceley.server.model.ServiceProvider;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.Religion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ServiceProviderMapper {
    @Mapping(target = "id", source="serviceProviderRequestDto.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "qualificationCertificate", source = "qualificationCertificate")
    @Mapping(target = "image1", source = "image1")
    @Mapping(target = "image2", source = "image2")
    @Mapping(target = "image3", source = "image3")
    @Mapping(target = "idProof", source = "idProof")
    @Mapping(target = "addressProof", source = "addressProof")
    public abstract ServiceProvider mapToModel(ServiceProviderRequestDto serviceProviderRequestDto, User user,
                                               String qualificationCertificate, String image1, String image2,
                                               String image3, String idProof, String addressProof);

    @Mapping(target = "user", expression = "java(getDtoUser(serviceProvider))")
    public abstract ServiceProviderResponseDto mapToDto(ServiceProvider serviceProvider);

    public DtoUser getDtoUser(ServiceProvider serviceProvider){
        User user = serviceProvider.getUser();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }
}
