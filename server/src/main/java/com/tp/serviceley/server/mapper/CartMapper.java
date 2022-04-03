package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.CartItemResponseDto;
import com.tp.serviceley.server.dto.CartRequestDto;
import com.tp.serviceley.server.dto.CartResponseDto;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.dto_related.*;
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
public abstract class CartMapper {
    @Autowired
    private CartItemMapper cartItemMapper;

    @Mapping(target = "id", source = "cartRequestDto.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "items", source = "items")
    public abstract Cart mapToModel(CartRequestDto cartRequestDto, User user, Coupon coupon,
                                    SpecialDiscount specialDiscount, List<CartItem> items);

    @Mapping(target = "user", expression = "java(getDtoUser(cart))")
    @Mapping(target = "items", expression = "java(getCartItems(cart))")
    public abstract CartResponseDto mapToDto(Cart cart);

    DtoUser getDtoUser(Cart cart){
        User user = cart.getUser();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }

    List<CartItemResponseDto> getCartItems(Cart cart){
        List<CartItem> items = cart.getItems();
        return items.stream().map(e -> cartItemMapper.mapToDto(e)).collect(Collectors.toList());
    }
}
