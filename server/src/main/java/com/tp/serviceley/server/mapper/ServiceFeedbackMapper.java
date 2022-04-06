package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ServiceFeedbackRequestDto;
import com.tp.serviceley.server.dto.ServiceFeedbackResponseDto;
import com.tp.serviceley.server.model.OrderItem;
import com.tp.serviceley.server.model.ServiceFeedback;
import com.tp.serviceley.server.model.ServiceProvider;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.dto_related.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ServiceFeedbackMapper {
    @Mapping(target = "id", source = "serviceFeedbackRequestDto.id")
    @Mapping(target = "serviceSubtype", source = "serviceSubtype")
    @Mapping(target = "serviceProvider", source = "serviceProvider")
    @Mapping(target = "orderItem", source = "orderItem")
    public abstract ServiceFeedback mapToModel(ServiceFeedbackRequestDto serviceFeedbackRequestDto, ServiceSubtype
            serviceSubtype, ServiceProvider serviceProvider, OrderItem orderItem);

    @Mapping(target = "serviceSubtype", expression = "java(getDtoServiceSubtype(serviceFeedback))")
    @Mapping(target = "serviceProvider", expression = "java(getDtoServiceProvider(serviceFeedback))")
    @Mapping(target = "orderItem", expression = "java(getDtoOrderItem(serviceFeedback))")
    public abstract ServiceFeedbackResponseDto mapToDto(ServiceFeedback serviceFeedback);

    DtoServiceSubtype getDtoServiceSubtype(ServiceFeedback serviceFeedback){
        ServiceSubtype serviceSubtype = serviceFeedback.getServiceSubtype();
        return new DtoServiceSubtype(serviceSubtype.getId(), serviceSubtype.getSubtype());
    }

    DtoServiceProvider getDtoServiceProvider(ServiceFeedback serviceFeedback){
        ServiceProvider serviceProvider = serviceFeedback.getServiceProvider();
        DtoUser dtoUser = new DtoUser(serviceProvider.getUser().getId(), serviceProvider.getUser().getFirstName(),
                serviceProvider.getUser().getLastName());
        return new DtoServiceProvider();
    }

    DtoOrderItem getDtoOrderItem(ServiceFeedback serviceFeedback){
        OrderItem orderItem = serviceFeedback.getOrderItem();
        DtoOrder dtoOrder = new DtoOrder(orderItem.getOrder().getId(), orderItem.getOrder().getDisplayOrderId(),
                orderItem.getOrder().getCreatedAt());
        DtoServiceSubtype dtoServiceSubtype = new DtoServiceSubtype(orderItem.getServiceSubtype().getId(),
                orderItem.getServiceSubtype().getSubtype());
        return new DtoOrderItem(orderItem.getId(), dtoOrder, orderItem.getServiceType(), dtoServiceSubtype);
    }
}
