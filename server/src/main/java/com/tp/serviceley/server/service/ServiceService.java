package com.tp.serviceley.server.service;

import com.tp.serviceley.server.dto.ServiceTypeResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.repository.ServiceTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceService {
    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceType createServiceType(String serviceName){
        Optional<ServiceType> serviceType = serviceTypeRepository.findByType(serviceName);
        if(serviceType.isPresent()){
            throw new BackendException("Service already exist");
        }
        return serviceTypeRepository.save(new ServiceType(serviceName));
    }
}
