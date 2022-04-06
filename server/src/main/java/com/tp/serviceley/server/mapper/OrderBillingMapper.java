package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.OrderBillingRequestDto;
import com.tp.serviceley.server.dto.OrderBillingResponseDto;
import com.tp.serviceley.server.model.Order;
import com.tp.serviceley.server.model.OrderBilling;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.dto_related.DtoOrder;
import com.tp.serviceley.server.model.dto_related.DtoUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class OrderBillingMapper {

    @Mapping(target = "id", source = "orderBillingRequestDto.id")
    @Mapping(target = "status", source = "orderBillingRequestDto.status")
    @Mapping(target = "estimatedAmount", source = "orderBillingRequestDto.estimatedAmount")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "verifiedBy", source = "verifiedBy")
    @Mapping(target = "updatedBy", source = "updatedBy")
    public abstract OrderBilling mapToModel(OrderBillingRequestDto orderBillingRequestDto, Order order,
                                            User verifiedBy, User updatedBy);

    @Mapping(target = "order", expression = "java(getDtoOrder(orderBilling))")
    @Mapping(target = "verifiedBy", expression = "java(getVerifiedByDtoUser(orderBilling))")
    @Mapping(target = "updatedBy", expression = "java(getUpdatedByDtoUser(orderBilling))")
    public abstract OrderBillingResponseDto mapToDto(OrderBilling orderBilling);

    DtoOrder getDtoOrder(OrderBilling orderBilling){
        Order order = orderBilling.getOrder();
        return new DtoOrder(order.getId(), order.getDisplayOrderId(), order.getCreatedAt());
    }

    DtoUser getVerifiedByDtoUser(OrderBilling orderBilling){
        User user = orderBilling.getVerifiedBy();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }

    DtoUser getUpdatedByDtoUser(OrderBilling orderBilling){
        User user = orderBilling.getUpdatedBy();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }
}
