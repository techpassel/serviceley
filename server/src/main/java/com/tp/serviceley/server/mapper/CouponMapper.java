package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.CouponRequestDto;
import com.tp.serviceley.server.dto.CouponResponseDto;
import com.tp.serviceley.server.model.Coupon;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class CouponMapper {
    @Mapping(target = "id", source = "couponRequestDto.id")
    @Mapping(target = "createdBy", source = "createdByUser")
    @Mapping(target = "updatedBy", source = "updatedByUser")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Coupon mapToModel(CouponRequestDto couponRequestDto, User createdByUser, User updatedByUser);

    @Mapping(target = "createdBy", expression = "java(getDtoCreatedByUser(coupon))")
    @Mapping(target = "updatedBy", expression = "java(getDtoUpdatedByUser(coupon))")
    public abstract CouponResponseDto mapToDto(Coupon coupon);

    public DtoUser getDtoCreatedByUser(Coupon coupon){
        User user = coupon.getCreatedBy();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }

    public DtoUser getDtoUpdatedByUser(Coupon coupon){
        User user = coupon.getUpdatedBy();
        return user != null ? new DtoUser(user.getId(), user.getFirstName(), user.getLastName()) : null;
    }
}
