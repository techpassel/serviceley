package com.tp.serviceley.server.service;

import com.tp.serviceley.server.dto.ServiceSubtypeRequestDto;
import com.tp.serviceley.server.dto.ServiceSubtypeResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.ServiceSubtypeMapper;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.repository.ServiceSubtypeRepository;
import com.tp.serviceley.server.repository.ServiceTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceService {
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceSubtypeRepository serviceSubtypeRepository;
    private final ServiceSubtypeMapper serviceSubtypeMapper;

    public ServiceType createServiceType(ServiceType service){
        return serviceTypeRepository.save(service);
    }

    public void deleteServiceType(Long id){
        try {
            serviceTypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BackendException("Service type with given id doesn't exist.", e);
        }
    }

    public ServiceSubtypeResponseDto createServiceSubtype(ServiceSubtypeRequestDto serviceSubtypeRequestDto){
        if(serviceSubtypeRequestDto.getId() == null) {
            Optional<ServiceSubtype> optionalServiceSubtype = serviceSubtypeRepository.
                    findByTypeAndSubtype(serviceSubtypeRequestDto.getTypeId(), serviceSubtypeRequestDto.getSubtype());
            if (optionalServiceSubtype.isPresent()) {
                throw new BackendException("Subtype already exist.");
            }
        }
        ServiceType serviceType = serviceTypeRepository.findById(serviceSubtypeRequestDto.getTypeId())
                .orElseThrow(() -> new BackendException("Service type with given id doesn't exist."));
        List<ServiceSubtype> optionalServices = new ArrayList<>();
        serviceSubtypeRequestDto.getOptionalServices().forEach(id -> {
            optionalServices.add(serviceSubtypeRepository.findById((Long) id).orElseThrow(() ->
                    new BackendException("Service subtype with given id doesn't exist.")));
        });
        ServiceSubtype sst = serviceSubtypeMapper.mapToModel(serviceSubtypeRequestDto, serviceType, optionalServices);
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.save(sst);
        return serviceSubtypeMapper.mapToDto(serviceSubtype);
    }

    public void deleteServiceSubtype(Long id){
        try{
        serviceSubtypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BackendException("Service type with given id doesn't exist.", e);
        }
    }
}
