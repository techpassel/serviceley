package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.ServiceProviderRequestDto;
import com.tp.serviceley.server.dto.ServiceProviderResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.repository.ServiceProviderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceProviderService {
    ServiceProviderRepository serviceProviderRepository;

    public ServiceProviderResponseDto createServiceProvider(ServiceProviderRequestDto serviceProviderRequestDto){
        return new ServiceProviderResponseDto();
    }

    public void deleteServiceProvider(Long id){
        try {
            serviceProviderRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Service unit with given id doesn't exist.", e);
        }
    }
}
