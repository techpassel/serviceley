package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.OrderItemResponseDto;
import com.tp.serviceley.server.dto.OrderRequestDto;
import com.tp.serviceley.server.dto.OrderResponseDto;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.dto_related.DtoCoupon;
import com.tp.serviceley.server.model.dto_related.DtoSpecialDiscount;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class OrderMapper {
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Mapping(target = "id", source = "orderRequestDto.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "coupon", source = "coupon")
    @Mapping(target = "specialDiscount", source = "specialDiscount")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Order mapToModel(OrderRequestDto orderRequestDto, User user, Coupon coupon, SpecialDiscount
            specialDiscount, List<OrderItem> items);

    @Mapping(target = "user", expression = "java(getDtoUser(order))")
    @Mapping(target = "coupon", expression = "java(getDtoCoupon(order))")
    @Mapping(target = "specialDiscount", expression = "java(getDtoSpecialDiscount(order))")
    @Mapping(target = "items", expression = "java(getOrderItems(order))")
    public abstract OrderResponseDto mapToDto(Order order);

    DtoUser getDtoUser(Order order){
        User user = order.getUser();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }

    DtoCoupon getDtoCoupon(Order order){
        Coupon coupon = order.getCoupon();
        return new DtoCoupon(coupon.getId(), coupon.getCode(), coupon.getAmount(), coupon.getAmountIn());
    }

    DtoSpecialDiscount getDtoSpecialDiscount(Order order){
        SpecialDiscount specialDiscount = order.getSpecialDiscount();
        return new DtoSpecialDiscount(specialDiscount.getId(), specialDiscount.getCode(), specialDiscount.getAmount(),
                specialDiscount.getAmountIn());
    }

    List<OrderItemResponseDto> getOrderItems(Order order){
        List<OrderItem> items = order.getItems();
        return items.stream().map(e -> orderItemMapper.mapToDto(e)).collect(Collectors.toList());
    }
}
