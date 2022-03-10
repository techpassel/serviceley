package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.AddressRequestDto;
import com.tp.serviceley.server.dto.AddressResponseDto;
import com.tp.serviceley.server.model.Address;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class AddressMapper {
    @Mapping(target = "id", source = "addressRequestDto.id")
    @Mapping(target = "user", source = "user")
    public abstract Address mapToModel(AddressRequestDto addressRequestDto, User user);

    @Mapping(target = "user", expression = "java(getDtoUser(address))")
    public abstract AddressResponseDto mapToDto(Address address);

    public DtoUser getDtoUser(Address address){
        User user = address.getUser();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }
}