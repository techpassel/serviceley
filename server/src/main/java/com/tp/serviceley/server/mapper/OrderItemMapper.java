package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.OrderItemRequestDto;
import com.tp.serviceley.server.dto.OrderItemResponseDto;
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
public abstract class OrderItemMapper {

    @Mapping(target = "id", source = "orderItemRequestDto.id")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "serviceType", source = "serviceType")
    @Mapping(target = "serviceSubtype", source = "serviceSubtype")
    @Mapping(target = "serviceUnit", source = "serviceUnit")
    @Mapping(target = "serviceFrequency", source = "serviceFrequency")
    @Mapping(target = "offer", source = "offer")
    public abstract OrderItem mapToModel(OrderItemRequestDto orderItemRequestDto, Order order, ServiceSubtype
            serviceType, ServiceSubtype serviceSubtype, ServiceUnit serviceUnit, ServiceFrequency serviceFrequency,
                                         Offer offer);

    @Mapping(target = "orderId", expression = "java(getOrderId(orderItem))")
    @Mapping(target = "serviceType", expression = "java(getServiceType(orderItem))")
    @Mapping(target = "serviceSubtype", expression = "java(getDtoServiceSubtype(orderItem))")
    @Mapping(target = "serviceUnit", expression = "java(getDtoServiceUnit(orderItem))")
    @Mapping(target = "serviceFrequency", expression = "java(getDtoServiceFrequency(orderItem))")
    @Mapping(target = "offer", expression = "java(getDtoOffer(orderItem))")
    public abstract OrderItemResponseDto mapToDto(OrderItem orderItem);

    Long getOrderId(OrderItem orderItem){
        return orderItem.getOrder().getId();
    }

    ServiceType getServiceType(OrderItem orderItem){
        return orderItem.getServiceType();
    }

    DtoServiceSubtype getDtoServiceSubtype(OrderItem orderItem){
        ServiceSubtype serviceSubtype = orderItem.getServiceSubtype();
        return new DtoServiceSubtype(serviceSubtype.getId(), serviceSubtype.getSubtype());
    }

    DtoServiceUnit getDtoServiceUnit(OrderItem orderItem){
        ServiceUnit serviceUnit = orderItem.getServiceUnit();
        return new DtoServiceUnit(serviceUnit.getId(), serviceUnit.getUnitLimit(), serviceUnit.getServiceUnitMeasure());
    }

    DtoServiceFrequency getDtoServiceFrequency(OrderItem orderItem){
        ServiceFrequency serviceFrequency = orderItem.getServiceFrequency();
        return new DtoServiceFrequency(serviceFrequency.getId(), serviceFrequency.getFrequency());
    }

    DtoOffer getDtoOffer(OrderItem orderItem){
        Offer offer = orderItem.getOffer();
        return new DtoOffer(offer.getId(), offer.getAmount(), offer.getAmountIn());
    }
}
