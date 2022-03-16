package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.CartItemRequestDto;
import com.tp.serviceley.server.dto.CartItemResponseDto;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.dto_related.DtoOffer;
import com.tp.serviceley.server.model.dto_related.DtoServiceFrequency;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import com.tp.serviceley.server.model.dto_related.DtoServiceUnit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class CartItemMapper {
    @Mapping(target = "id", source = "cartItemRequestDto.id")
    @Mapping(target = "cart", source = "cart")
    @Mapping(target = "serviceType", source = "serviceType")
    @Mapping(target = "serviceSubtype", source = "serviceSubtype")
    @Mapping(target = "offer", source = "offer")
    @Mapping(target = "serviceUnit", source = "serviceUnit")
    public abstract CartItem mapToModel(CartItemRequestDto cartItemRequestDto, Cart cart, ServiceType
            serviceType, ServiceSubtype serviceSubtype, Offer offer, ServiceUnit serviceUnit, ServiceFrequency
            serviceFrequency);

    @Mapping(target = "cartId", source = "java(getCartId(cartItem))")
    @Mapping(target = "serviceSubtype", expression = "java(getDtoServiceSubtype(cartItem))")
    @Mapping(target = "offer", expression = "java(getDtoOffer(cartItem))")
    @Mapping(target = "serviceUnit", expression = "java(getDtoServiceUnit(cartItem))")
    @Mapping(target = "serviceFrequency", expression = "java(getDtoServiceFrequency(cartItem))")
    public abstract CartItemResponseDto mapToDto(CartItem cartItem);

    Long getCartId(CartItem cartItem){
        return cartItem.getCart().getId();
    }

    DtoServiceSubtype getDtoServiceSubtype(CartItem cartItem){
        ServiceSubtype serviceSubtype = cartItem.getServiceSubtype();
        return new DtoServiceSubtype(serviceSubtype.getId(), serviceSubtype.getSubtype());
    }

    DtoOffer getDtoOffer(CartItem cartItem){
        Offer offer = cartItem.getOffer();
        return new DtoOffer(offer.getId(), offer.getAmount(), offer.getAmountIn());
    }

    DtoServiceUnit getDtoServiceUnit(CartItem cartItem){
        ServiceUnit serviceUnit = cartItem.getServiceUnit();
        return new DtoServiceUnit(serviceUnit.getId(), serviceUnit.getUnitLimit(), serviceUnit.getServiceUnitMeasure());
    }

    DtoServiceFrequency getDtoServiceFrequency(CartItem cartItem){
        ServiceFrequency serviceFrequency = cartItem.getServiceFrequency();
        return new DtoServiceFrequency(serviceFrequency.getId(), serviceFrequency.getFrequency());
    }
}
