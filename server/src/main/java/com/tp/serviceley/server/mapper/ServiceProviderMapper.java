package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ServiceProviderRequestDto;
import com.tp.serviceley.server.dto.ServiceProviderResponseDto;
import com.tp.serviceley.server.model.ServiceProvider;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoUser;
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
    @Mapping(target = "qualification_certificate", source = "qualification_certificate")
    @Mapping(target = "image1", source = "image1")
    @Mapping(target = "image2", source = "image2")
    @Mapping(target = "image3", source = "image3")
    @Mapping(target = "id_proof", source = "id_proof")
    @Mapping(target = "address_proof", source = "address_proof")
    public abstract ServiceProvider mapToModel(ServiceProviderRequestDto serviceProviderRequestDto, User user,
                                               String qualification_certificate, String image1, String image2,
                                               String image3, String id_proof, String address_proof);

    @Mapping(target = "", expression = "java(getDtoUser(serviceProvider.user))")
    public abstract ServiceProviderResponseDto mapToDto(ServiceProvider serviceProvider);

    private DtoUser getDtoUser(User user){
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }
}
