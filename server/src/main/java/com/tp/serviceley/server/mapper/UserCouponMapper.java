package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.UserCouponResponseDto;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.UserCoupon;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class UserCouponMapper {

    @Mapping(target = "user", expression = "java(getUser(userCoupon))")
    @Mapping(target = "couponId", source = "userCoupon.coupon.id")
    public abstract UserCouponResponseDto mapToDto(UserCoupon userCoupon);

    public DtoUser getUser(UserCoupon userCoupon){
        User user = userCoupon.getUser();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }
}
