package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.OfferRequestDto;
import com.tp.serviceley.server.dto.OfferResponseDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.OfferMapper;
import com.tp.serviceley.server.model.Offer;
import com.tp.serviceley.server.model.ServiceSubtype;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.repository.OfferRepository;
import com.tp.serviceley.server.repository.ServiceSubtypeRepository;
import com.tp.serviceley.server.repository.ServiceTypeRepository;
import com.tp.serviceley.server.service.CommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class OfferService {
    private final OfferMapper offerMapper;
    private final CommonService commonService;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceSubtypeRepository serviceSubtypeRepository;
    private final OfferRepository offerRepository;

    public OfferResponseDto createOffer(OfferRequestDto offerRequestDto){
        User currentUser = commonService.getCurrentUser();
        Long serviceTypeId = offerRequestDto.getServiceTypeId();
        Long serviceSubtypeId = offerRequestDto.getServiceSubtypeId();
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId).orElseThrow(() ->
                new BackendException("Service type with given id doesn't exist."));
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(serviceSubtypeId).orElseThrow(() ->
                new BackendException("Service subtype with given id doesn't exist."));
        Offer offer = offerMapper.mapToModel(offerRequestDto, currentUser, serviceType, serviceSubtype);
        return offerMapper.mapToDto(offerRepository.save(offer));
    }

    public void deleteOffer(Long id){
        try {
            offerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BackendException("Offer with given id doesn't exist.", e);
        }
    }
}
