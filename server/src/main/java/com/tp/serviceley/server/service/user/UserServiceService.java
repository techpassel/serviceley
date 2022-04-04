package com.tp.serviceley.server.service.user;

import com.tp.serviceley.server.dto.ServiceSubtypeResponseDto;
import com.tp.serviceley.server.dto.ServiceUnitResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.ServiceSubtypeMapper;
import com.tp.serviceley.server.mapper.ServiceUnitMapper;
import com.tp.serviceley.server.model.ServiceFrequency;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.ServiceUnit;
import com.tp.serviceley.server.model.dto_related.DtoServiceFrequency;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import com.tp.serviceley.server.model.dto_related.DtoServiceUnit;
import com.tp.serviceley.server.repository.ServiceFrequencyRepository;
import com.tp.serviceley.server.repository.ServiceSubtypeRepository;
import com.tp.serviceley.server.repository.ServiceTypeRepository;
import com.tp.serviceley.server.repository.ServiceUnitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceService {
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceSubtypeRepository serviceSubtypeRepository;
    private final ServiceSubtypeMapper serviceSubtypeMapper;
    private final ServiceUnitRepository serviceUnitRepository;
    private final ServiceFrequencyRepository serviceFrequencyRepository;
    private final ServiceUnitMapper serviceUnitMapper;

    public List<ServiceType> getServiceTypes(){
        return serviceTypeRepository.findAll();
    }

    public List<DtoServiceSubtype> getServiceSubtypes(Long typeId){
        ServiceType type = serviceTypeRepository.findById(typeId).orElseThrow
                (() -> new BackendException("Service type not found."));
        List<ServiceSubtype> subtypes = serviceSubtypeRepository.findByType(type);
        return subtypes.stream().map(s -> new DtoServiceSubtype(s.getId(), s.getSubtype()))
                .collect(Collectors.toList());
    }

    public ServiceSubtypeResponseDto getServiceSubtypeDetails(Long id){
        ServiceSubtype subtype = serviceSubtypeRepository.findById(id).orElseThrow(() ->
                new BackendException("Service subtype not found."));
        return serviceSubtypeMapper.mapToDto(subtype);
    }

    public List<DtoServiceUnit> getServiceUnits(Long subtypeId){
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(subtypeId).orElseThrow(() ->
                new BackendException("Service subtype not found."));
        List<ServiceUnit> serviceUnits = serviceUnitRepository.findByServiceSubtype(serviceSubtype);
        return serviceUnits.stream().map(s -> new DtoServiceUnit(s.getId(), s.getUnitLimit(), s.getServiceUnitType(),
                s.getServiceUnitMeasure())).collect(Collectors.toList());
    }

    public ServiceUnitResponseDto getServiceUnitDetails(Long id){
        ServiceUnit serviceUnit = serviceUnitRepository.findById(id).orElseThrow(() ->
                new BackendException("Service unit not found"));
        return serviceUnitMapper.mapToDto(serviceUnit);
    }

    public List<DtoServiceFrequency> getServiceFrequencies(Long subtypeId){
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(subtypeId).orElseThrow(() ->
                new BackendException("Service subtype not found."));
        List<ServiceFrequency> serviceFrequencies = serviceFrequencyRepository.findByServiceSubtype(serviceSubtype);
        return serviceFrequencies.stream().map(s -> new DtoServiceFrequency(s.getId(), s.getFrequency(),
                s.getFrequencyDetails())).collect(Collectors.toList());
    }

}
