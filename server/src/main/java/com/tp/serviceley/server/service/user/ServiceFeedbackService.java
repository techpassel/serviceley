package com.tp.serviceley.server.service.user;

import com.tp.serviceley.server.dto.ServiceFeedbackRequestDto;
import com.tp.serviceley.server.dto.ServiceFeedbackResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.ServiceFeedbackMapper;
import com.tp.serviceley.server.model.OrderItem;
import com.tp.serviceley.server.model.ServiceFeedback;
import com.tp.serviceley.server.model.ServiceProvider;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.repository.OrderItemRepository;
import com.tp.serviceley.server.repository.ServiceFeedbackRepository;
import com.tp.serviceley.server.repository.ServiceProviderRepository;
import com.tp.serviceley.server.repository.ServiceSubtypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
@Slf4j
public class ServiceFeedbackService {
    private final ServiceSubtypeRepository serviceSubtypeRepository;
    private final ServiceProviderRepository serviceProviderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ServiceFeedbackMapper serviceFeedbackMapper;
    private final ServiceFeedbackRepository serviceFeedbackRepository;

    public ServiceFeedbackResponseDto createServiceFeedback(ServiceFeedbackRequestDto serviceFeedbackRequestDto) {
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(serviceFeedbackRequestDto.getServiceSubtypeId())
                .orElseThrow(() -> new BackendException("Service subtype not found."));
        ServiceProvider serviceProvider = serviceProviderRepository.findById(serviceFeedbackRequestDto
                .getServiceProviderId()).orElseThrow(() -> new BackendException("Service provider not found."));
        OrderItem orderItem = orderItemRepository.findById(serviceFeedbackRequestDto.getOrderItemId()).orElseThrow(() ->
                    new BackendException("Order Item not found."));
        ServiceFeedback serviceFeedback = serviceFeedbackRepository.save(serviceFeedbackMapper.mapToModel(
                serviceFeedbackRequestDto, serviceSubtype, serviceProvider, orderItem));
        return serviceFeedbackMapper.mapToDto(serviceFeedback);
    }

    public void deleteServiceFeedback(Long id){
        try {
            serviceFeedbackRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Service feedback with given id doesn't exist.", e);
        }
    }
}
