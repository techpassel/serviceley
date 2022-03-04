package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.SpecialDiscountRequestDto;
import com.tp.serviceley.server.dto.SpecialDiscountResponseDto;
import com.tp.serviceley.server.model.SpecialDiscount;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class SpecialDiscountMapper {
    @Mapping(target = "id", source = "specialDiscountRequestDto.id")
    @Mapping(target = "createdBy", source = "user")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract SpecialDiscount mapToModel(SpecialDiscountRequestDto specialDiscountRequestDto, User user,
                                               String code);

    @Mapping(target = "createdBy", expression = "java(getCreatedByDtoUser(specialDiscount))")
    public abstract SpecialDiscountResponseDto mapToDto(SpecialDiscount specialDiscount);

    public DtoUser getCreatedByDtoUser(SpecialDiscount specialDiscount) {
        User user = specialDiscount.getCreatedBy();
        return user != null ? new DtoUser(user.getId(), user.getFirstName(), user.getLastName()) : null;
    }
}
