package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.*;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.model.dto_related.DtoOrder;
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
public abstract class ComplainMapper {
    @Autowired
    private ServiceProviderMapper serviceProviderMapper;

    @Autowired
    private OrderItemMapper orderItemMapper;

    @Autowired
    private ComplainMessageMapper complainMessageMapper;

    @Autowired
    private OrderBillingMapper orderBillingMapper;

    @Mapping(target = "id", source = "complainRequestDto.id")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "serviceProvider", source = "serviceProvider")
    @Mapping(target = "order", source = "order")
    @Mapping(target = "orderBilling", source = "orderBilling")
    @Mapping(target = "orderItem", source = "orderItem")
    @Mapping(target = "finalRemarkBy", source = "finalRemarkBy")
    @Mapping(target = "messages", source = "messages")
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    public abstract Complain mapToModel(ComplainRequestDto complainRequestDto, User user,
                                        ServiceProvider serviceProvider, Order order,
                                        OrderBilling orderBilling, OrderItem orderItem, Staff finalRemarkBy,
                                        List<ComplainMessage> messages);

    @Mapping(target = "user", expression = "java(getDtoUser(complain))")
    @Mapping(target = "serviceProvider", expression = "java(getServiceProvider(complain))")
    @Mapping(target = "order", expression = "java(getDtoOrder(complain))")
    @Mapping(target = "orderBilling", expression = "java(getOrderBilling(complain))")
    @Mapping(target = "orderItem", expression = "java(getOrderItem(complain))")
    @Mapping(target = "messages", expression = "java(getMessages(complain))")
    public abstract ComplainResponseDto mapToDto(Complain complain);

    DtoUser getDtoUser(Complain complain){
        User user = complain.getUser();
        return new DtoUser(user.getId(), user.getFirstName(), user.getLastName());
    }

    ServiceProviderResponseDto getServiceProvider(Complain complain){
        ServiceProvider serviceProvider = complain.getServiceProvider();
        return serviceProviderMapper.mapToDto(serviceProvider);
    }

    DtoOrder getDtoOrder(Complain complain){
        Order order = complain.getOrder();
        return new DtoOrder(order.getId(), order.getDisplayOrderId(), order.getCreatedAt());
    }

    OrderItemResponseDto getOrderItem(Complain complain){
        OrderItem orderItem = complain.getOrderItem();
        return orderItemMapper.mapToDto(orderItem);
    }

    OrderBillingResponseDto getOrderBilling(Complain complain){
        OrderBilling orderBilling = complain.getOrderBilling();
        return orderBillingMapper.mapToDto(orderBilling);
    }

    List<ComplainMessageResponseDto> getMessages(Complain complain){
        List<ComplainMessage> messages = complain.getMessages();
        return messages.stream().map(e -> complainMessageMapper.mapToDto(e)).collect(Collectors.toList());
    }
}
