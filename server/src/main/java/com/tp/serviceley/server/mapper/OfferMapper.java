package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.OfferRequestDto;
import com.tp.serviceley.server.dto.OfferResponseDto;
import com.tp.serviceley.server.model.Offer;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class OfferMapper {
    @Mapping(target = "createdBy", source = "createdByUser")
    @Mapping(target = "updatedBy", source = "updatedByUser")
    @Mapping(target = "serviceType", source = "serviceType")
    @Mapping(target = "serviceSubtype", source = "serviceSubtype")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "id", source = "offerRequestDto.id")
    public abstract Offer mapToModel(OfferRequestDto offerRequestDto, User createdByUser, User updatedByUser,
                                     ServiceType serviceType, ServiceSubtype serviceSubtype);

    @Mapping(target = "createdBy", expression = "java(getDtoCreatedByUser(offer))")
    @Mapping(target = "updatedBy", expression = "java(getDtoUpdatedByUser(offer))")
    @Mapping(target = "serviceSubtype", expression = "java(getDtoServiceSubtype(offer))")
    public abstract OfferResponseDto mapToDto(Offer offer);

    DtoUser getDtoCreatedByUser(Offer offer) {
        User user = offer.getCreatedBy();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }

    DtoUser getDtoUpdatedByUser(Offer offer) {
        User user = offer.getUpdatedBy();
        return user != null ? new DtoUser(user.getId(), user.getFirstName(), user.getLastName()) : null;
    }

    DtoServiceSubtype getDtoServiceSubtype(Offer offer){
        ServiceSubtype serviceSubtype = offer.getServiceSubtype();
        return new DtoServiceSubtype(serviceSubtype.getId(), serviceSubtype.getSubtype());
    }
}
