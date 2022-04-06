package com.tp.serviceley.server.mapper;

import com.tp.serviceley.server.dto.ServiceDeliverRecordResponseDto;
import com.tp.serviceley.server.dto.ServiceDeliveryRecordRequestDto;
import com.tp.serviceley.server.model.OrderItem;
import com.tp.serviceley.server.model.ServiceDeliveryRecord;
import com.tp.serviceley.server.model.ServiceProvider;
import com.tp.serviceley.server.model.Staff;
import com.tp.serviceley.server.model.dto_related.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring")
public abstract class ServiceDeliveryRecordMapper {
    @Mapping(target = "id", source = "serviceDeliveryRecordRequestDto.id")
    @Mapping(target = "orderItem", source = "orderItem")
    @Mapping(target = "serviceProvider", source = "serviceProvider")
    @Mapping(target = "staff", source = "staff")
    public abstract ServiceDeliveryRecord mapToModel(ServiceDeliveryRecordRequestDto serviceDeliveryRecordRequestDto,
                                                     OrderItem orderItem, ServiceProvider serviceProvider, Staff staff);

    @Mapping(target = "orderItem", expression = "java(getDtoOrderItem(serviceDeliveryRecord))")
    @Mapping(target = "serviceProvider", expression = "java(getDtoServiceProvider(serviceDeliveryRecord))")
    @Mapping(target = "staff", expression = "java(getDtoStaff(serviceDeliveryRecord))")
    public abstract ServiceDeliverRecordResponseDto mapToDto(ServiceDeliveryRecord serviceDeliveryRecord);

    DtoOrderItem getDtoOrderItem(ServiceDeliveryRecord serviceDeliveryRecord){
        OrderItem orderItem = serviceDeliveryRecord.getOrderItem();
        DtoOrder dtoOrder = new DtoOrder(orderItem.getOrder().getId(), orderItem.getOrder().getDisplayOrderId(),
                orderItem.getOrder().getCreatedAt());
        DtoServiceSubtype dtoServiceSubtype = new DtoServiceSubtype(orderItem.getServiceSubtype().getId(),
                orderItem.getServiceSubtype().getSubtype());
        return new DtoOrderItem(orderItem.getId(), dtoOrder, orderItem.getServiceType(), dtoServiceSubtype);
    }

    DtoServiceProvider getDtoServiceProvider(ServiceDeliveryRecord serviceDeliveryRecord){
        ServiceProvider serviceProvider = serviceDeliveryRecord.getServiceProvider();
        DtoUser dtoUser = new DtoUser(serviceProvider.getUser().getId(), serviceProvider.getUser().getFirstName(),
                serviceProvider.getUser().getLastName());
        return new DtoServiceProvider(serviceProvider.getId(), dtoUser);
    }

    DtoStaff getDtoStaff(ServiceDeliveryRecord serviceDeliveryRecord){
        Staff staff = serviceDeliveryRecord.getStaff();
        DtoUser dtoUser = new DtoUser(staff.getUser().getId(), staff.getUser().getFirstName(),
                staff.getUser().getLastName());
        return new DtoStaff(staff.getId(), dtoUser);
    }
}
