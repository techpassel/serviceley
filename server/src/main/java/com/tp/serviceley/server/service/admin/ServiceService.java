package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.ServiceSubtypeRequestDto;
import com.tp.serviceley.server.dto.ServiceSubtypeResponseDto;
import com.tp.serviceley.server.dto.ServiceUnitRequestDto;
import com.tp.serviceley.server.dto.ServiceUnitResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.ServiceSubtypeMapper;
import com.tp.serviceley.server.mapper.ServiceUnitMapper;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.ServiceUnit;
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
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ServiceService {
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceSubtypeRepository serviceSubtypeRepository;
    private final ServiceSubtypeMapper serviceSubtypeMapper;
    private final ServiceUnitRepository serviceUnitRepository;
    private final ServiceUnitMapper serviceUnitMapper;

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
        Optional<ServiceSubtype> optionalServiceSubtype = serviceSubtypeRepository.
                findByTypeAndSubtype(serviceSubtypeRequestDto.getTypeId(), serviceSubtypeRequestDto.getSubtype());
        if (optionalServiceSubtype.isPresent()) {
            if(serviceSubtypeRequestDto.getId() == null) throw new BackendException("Service Subtype already exist.");
            else {
                ServiceSubtype subtype = optionalServiceSubtype.get();
                if (subtype.getId() != serviceSubtypeRequestDto.getId())
                    throw new BackendException("Some other service subtype with same type and subtype name already exist.");
            }
        }
        ServiceType serviceType = serviceTypeRepository.findById(serviceSubtypeRequestDto.getTypeId())
                .orElseThrow(() -> new BackendException("Service type with given id doesn't exist."));
        List<ServiceSubtype> optionalServices = new ArrayList<>();
        serviceSubtypeRequestDto.getOptionalServices().forEach(id -> {
            optionalServices.add(serviceSubtypeRepository.findById((Long) id).orElseThrow(() ->
                    new BackendException("Service subtype with given id doesn't exist.")));
        });
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.save(serviceSubtypeMapper.mapToModel(serviceSubtypeRequestDto, serviceType, optionalServices));
        return serviceSubtypeMapper.mapToDto(serviceSubtype);
    }

    public void deleteServiceSubtype(Long id){
        try{
            serviceSubtypeRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BackendException("Service type with given id doesn't exist.", e);
        }
    }

    public ServiceUnitResponseDto createServiceUnit(ServiceUnitRequestDto serviceUnitRequestDto){
        List<ServiceUnit> optionalServiceUnit = serviceUnitRepository.
                findBySubtypeUnitAndLimit(serviceUnitRequestDto.getServiceSubtypeId(),
                        serviceUnitRequestDto.getServiceUnitType().ordinal(), serviceUnitRequestDto.getUnitLimit());
        if (optionalServiceUnit.size() > 0) {
            if(serviceUnitRequestDto.getId() == null) throw new BackendException("Service unit already exist.");
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

    public void deleteServiceUnit(Long id){
        try{
            serviceUnitRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BackendException("Service unit with given id doesn't exist.", e);
        }
    }
}
