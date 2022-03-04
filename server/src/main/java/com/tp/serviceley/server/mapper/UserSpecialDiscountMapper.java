package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.UserSpecialDiscountRequestDto;
import com.tp.serviceley.server.dto.UserSpecialDiscountResponseDto;
import com.tp.serviceley.server.model.SpecialDiscount;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.UserSpecialDiscount;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class UserSpecialDiscountMapper {
    @Mapping(target = "id", source = "userSpecialDiscountRequestDto.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "specialDiscount", source = "specialDiscount")
    @Mapping(target = "issuedBy", source = "issuedBy")
    public abstract UserSpecialDiscount mapToModel(UserSpecialDiscountRequestDto userSpecialDiscountRequestDto,
                                                   User user, SpecialDiscount specialDiscount, User issuedBy);

    @Mapping(target = "user", expression = "java(getDtoUser(userSpecialDiscount.getUser()))")
    @Mapping(target = "issuedBy", expression = "java(getDtoUser(userSpecialDiscount.getIssuedBy()))")
    @Mapping(target = "approvedBy", expression = "java(getDtoUser(userSpecialDiscount.getApprovedBy()))")
    public abstract UserSpecialDiscountResponseDto mapToDto(UserSpecialDiscount userSpecialDiscount);

    public DtoUser getDtoUser(User user){
        if(user != null){
            return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
        } else {
            return null;
        }
    }
}
