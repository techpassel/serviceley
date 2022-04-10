package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.ServiceDeliveryRecordRequestDto;
import com.tp.serviceley.server.dto.ServiceDeliveryRecordResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.ServiceDeliveryRecordMapper;
import com.tp.serviceley.server.model.OrderItem;
import com.tp.serviceley.server.model.ServiceDeliveryRecord;
import com.tp.serviceley.server.model.ServiceProvider;
import com.tp.serviceley.server.model.Staff;
import com.tp.serviceley.server.repository.OrderItemRepository;
import com.tp.serviceley.server.repository.ServiceDeliveryRecordRepository;
import com.tp.serviceley.server.repository.ServiceProviderRepository;
import com.tp.serviceley.server.repository.StaffRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@AllArgsConstructor
@Service
@Slf4j
public class ServiceDeliveryRecordService {
    private final ServiceProviderRepository serviceProviderRepository;
    private final StaffRepository staffRepository;
    private final OrderItemRepository orderItemRepository;
    private final ServiceDeliveryRecordMapper serviceDeliveryRecordMapper;
    private final ServiceDeliveryRecordRepository serviceDeliveryRecordRepository;

    public ServiceDeliveryRecordResponseDto createServiceDeliveryRecord(
            ServiceDeliveryRecordRequestDto serviceDeliveryRecordRequestDto) {
        Long id = serviceDeliveryRecordRequestDto.getId();
        OrderItem orderItem = orderItemRepository.findById(serviceDeliveryRecordRequestDto.getOrderItemId())
                .orElseThrow(() -> new BackendException("Order item not found."));
        ServiceProvider serviceProvider = null;
        Staff staff = null;
        if (serviceDeliveryRecordRequestDto.getServiceProviderId() != null) {
            serviceProvider = serviceProviderRepository.findById(serviceDeliveryRecordRequestDto.getServiceProviderId())
                    .orElseThrow(() -> new BackendException("Service provider not found."));
        }
        if (serviceDeliveryRecordRequestDto.getStaffId() != null) {
            staff = staffRepository.findById(serviceDeliveryRecordRequestDto.getStaffId()).orElseThrow(() ->
                    new BackendException("Staff not found."));
        }
        if (serviceDeliveryRecordRequestDto.getDate() != null) {
            serviceDeliveryRecordRequestDto.setDate(LocalDate.now());
        }
        ServiceDeliveryRecord serviceDeliveryRecord = serviceDeliveryRecordRepository.save(serviceDeliveryRecordMapper.mapToModel
                (serviceDeliveryRecordRequestDto, orderItem, serviceProvider, staff));
        return serviceDeliveryRecordMapper.mapToDto(serviceDeliveryRecord);
    }

    public void deleteServiceDeliveryRecord(Long id) {
        try {
            serviceDeliveryRecordRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Service delivery record with given id doesn't exist.", e);
        }
    }
}
