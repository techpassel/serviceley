package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.*;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.ServiceFrequencyMapper;
import com.tp.serviceley.server.mapper.ServiceSubtypeMapper;
import com.tp.serviceley.server.mapper.ServiceUnitMapper;
import com.tp.serviceley.server.model.ServiceFrequency;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.ServiceUnit;
import com.tp.serviceley.server.repository.ServiceFrequencyRepository;
import com.tp.serviceley.server.repository.ServiceSubtypeRepository;
import com.tp.serviceley.server.repository.ServiceTypeRepository;
import com.tp.serviceley.server.repository.ServiceUnitRepository;
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
    private final ServiceUnitRepository serviceUnitRepository;
    private final ServiceUnitMapper serviceUnitMapper;
    private final ServiceFrequencyRepository serviceFrequencyRepository;
    private final ServiceFrequencyMapper serviceFrequencyMapper;

    public ServiceType createServiceType(ServiceType service) {
        return serviceTypeRepository.save(service);
    }

    public void deleteServiceType(Long id) {
        try {
            serviceTypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Service type with given id doesn't exist.", e);
        }
    }

    public ServiceSubtypeResponseDto createServiceSubtype(ServiceSubtypeRequestDto serviceSubtypeRequestDto) {
        Optional<ServiceSubtype> optionalServiceSubtype = serviceSubtypeRepository.
                findByTypeIdAndSubtype(serviceSubtypeRequestDto.getTypeId(), serviceSubtypeRequestDto.getSubtype());
        if (optionalServiceSubtype.isPresent()) {
            if (serviceSubtypeRequestDto.getId() == null) throw new BackendException("Service Subtype already exist.");
            else {
                ServiceSubtype subtype = optionalServiceSubtype.get();
                if (subtype.getId() != serviceSubtypeRequestDto.getId())
                    throw new BackendException("Some other service subtype with same type and subtype name already exist.");
            }
        }
        ServiceType serviceType = serviceTypeRepository.findById(serviceSubtypeRequestDto.getTypeId())
                .orElseThrow(() -> new BackendException("Service type with given id doesn't exist."));
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.save(serviceSubtypeMapper.mapToModel(serviceSubtypeRequestDto, serviceType));
        return serviceSubtypeMapper.mapToDto(serviceSubtype);
    }

    public void deleteServiceSubtype(Long id) {
        try {
            serviceSubtypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Service type with given id doesn't exist.", e);
        }
    }

    public ServiceUnitResponseDto createServiceUnit(ServiceUnitRequestDto serviceUnitRequestDto) {
        List<ServiceUnit> optionalServiceUnit = serviceUnitRepository.
                findBySubtypeUnitAndLimit(serviceUnitRequestDto.getServiceSubtypeId(),
                        serviceUnitRequestDto.getServiceUnitType().ordinal(), serviceUnitRequestDto.getUnitLimit());
        if (optionalServiceUnit.size() > 0) {
            if (serviceUnitRequestDto.getId() == null) throw new BackendException("Service unit already exist.");
            else {
                Long matchingServiceUnits = optionalServiceUnit.stream().filter(v -> v.getId() ==
                        serviceUnitRequestDto.getId()).count();
                if (matchingServiceUnits == 0) throw new BackendException("Some other service unit with same " +
                        "service subtype, unit type and unit limit already exist.");
            }
        }
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(serviceUnitRequestDto.getServiceSubtypeId())
                .orElseThrow(() -> new BackendException("Service subtype with given id doesn't exist."));
        ServiceUnit serviceUnit = serviceUnitRepository.save(serviceUnitMapper.mapToModel(serviceUnitRequestDto, serviceSubtype));
        return serviceUnitMapper.mapToDto(serviceUnit);
    }

    public void deleteServiceUnit(Long id) {
        try {
            serviceUnitRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Service unit with given id doesn't exist.", e);
        }
    }

    public ServiceFrequencyResponseDto createServiceFrequency(ServiceFrequencyRequestDto serviceFrequencyRequestDto) {
        List<ServiceFrequency> serviceFrequencies = serviceFrequencyRepository
                .findByServiceSubtypeAndFrequency(serviceFrequencyRequestDto.getServiceSubtypeId(),
                        serviceFrequencyRequestDto.getFrequency());
        if(serviceFrequencies.size() > 0){
            if(serviceFrequencyRequestDto.getId() == null) throw new BackendException("Service frequency already exist.");
            else {
                Long matchingServiceFrequencies = serviceFrequencies.stream().filter(v -> v.getId() ==
                        serviceFrequencyRequestDto.getId()).count();
                if(matchingServiceFrequencies == 0) throw new BackendException("Some other service frequency " +
                        "with same service subtype and frequency already exist.");
            }
        }
        ServiceType serviceType = serviceTypeRepository.findById(serviceFrequencyRequestDto.getServiceTypeId())
                .orElseThrow(() -> new BackendException("Service type with given id doesn't exist."));
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(serviceFrequencyRequestDto.
                getServiceSubtypeId()).orElseThrow(() ->
                new BackendException("Service subtype with given id doesn't exist."));
        ServiceFrequency serviceFrequency = serviceFrequencyRepository.save(serviceFrequencyMapper.
                mapToModel(serviceFrequencyRequestDto, serviceType, serviceSubtype));
        return serviceFrequencyMapper.mapToDto(serviceFrequency);
    }

    public void deleteServiceFrequency(Long id) {
        try {
            serviceFrequencyRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new BackendException("Service unit with given id doesn't exist.", e);
        }
    }
}
