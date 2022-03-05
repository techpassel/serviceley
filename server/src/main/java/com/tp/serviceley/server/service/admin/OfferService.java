package com.tp.serviceley.server.service.admin;

import com.tp.serviceley.server.dto.*;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.mapper.CouponMapper;
import com.tp.serviceley.server.mapper.OfferMapper;
import com.tp.serviceley.server.mapper.SpecialDiscountMapper;
import com.tp.serviceley.server.model.*;
import com.tp.serviceley.server.repository.*;
import com.tp.serviceley.server.service.CommonService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@AllArgsConstructor
@Slf4j
public class OfferService {
    private final OfferMapper offerMapper;
    private final CommonService commonService;
    private final ServiceTypeRepository serviceTypeRepository;
    private final ServiceSubtypeRepository serviceSubtypeRepository;
    private final OfferRepository offerRepository;
    private final CouponMapper couponMapper;
    private final CouponRepository couponRepository;
    private final SpecialDiscountMapper specialDiscountMapper;
    private final SpecialDiscountRepository specialDiscountRepository;

    public OfferResponseDto createOffer(OfferRequestDto offerRequestDto){
        User currentUser = commonService.getCurrentUser();
        User updatedByUser = null;
        User createdByUser;
        if(offerRequestDto.getId() != null) {
            Long id = offerRequestDto.getId();
            Offer offer = offerRepository.findById(id).orElseThrow(() -> new BackendException("Offer not found"));
            createdByUser = offer.getCreatedBy();
            updatedByUser = currentUser;
        } else createdByUser = currentUser;
        Long serviceTypeId = offerRequestDto.getServiceTypeId();
        Long serviceSubtypeId = offerRequestDto.getServiceSubtypeId();
        ServiceType serviceType = serviceTypeRepository.findById(serviceTypeId).orElseThrow(() ->
                new BackendException("Service type with given id doesn't exist."));
        ServiceSubtype serviceSubtype = serviceSubtypeRepository.findById(serviceSubtypeId).orElseThrow(() ->
                new BackendException("Service subtype with given id doesn't exist."));
        Offer offer = offerMapper.mapToModel(offerRequestDto, createdByUser, updatedByUser, serviceType, serviceSubtype);
        return offerMapper.mapToDto(offerRepository.save(offer));
    }

    public void deleteOffer(Long id){
        try {
            offerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BackendException("Offer with given id doesn't exist.", e);
        }
    }

    public CouponResponseDto createCoupon(CouponRequestDto couponRequestDto){
        User currentUser = commonService.getCurrentUser();
        User updatedByUser = null;
        User createdByUser;
        if(couponRequestDto.getId() != null) {
            Long id = couponRequestDto.getId();
            Offer offer = offerRepository.findById(id).orElseThrow(() -> new BackendException("Offer not found"));
            createdByUser = offer.getCreatedBy();
            updatedByUser = currentUser;
        } else createdByUser = currentUser;
        Coupon coupon = couponMapper.mapToModel(couponRequestDto, createdByUser, updatedByUser);
        return couponMapper.mapToDto(couponRepository.save(coupon));
    }

    public void deleteCoupon(Long id){
        try {
            couponRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BackendException("Offer with given id doesn't exist.", e);
        }
    }

    public SpecialDiscountResponseDto createSpecialDiscount(SpecialDiscountRequestDto specialDiscountRequestDto){
        User createdByUser;
        String code;
        if(specialDiscountRequestDto.getId() != null){
            SpecialDiscount specialDiscount = specialDiscountRepository.findById(specialDiscountRequestDto.getId())
                    .orElseThrow(() -> new BackendException("Special discount not found."));
            createdByUser = specialDiscount.getCreatedBy();
            code = specialDiscount.getCode();
        } else {
            createdByUser = commonService.getCurrentUser();
            Supplier<String> supplier = () -> UUID.randomUUID().toString().replace("-", "");
            code = supplier.get();
        }

        SpecialDiscount specialDiscount = specialDiscountMapper.mapToModel(specialDiscountRequestDto, createdByUser, code);
        return specialDiscountMapper.mapToDto(specialDiscountRepository.save(specialDiscount));
    }

    public void deleteSpecialDiscount(Long id){
        try {
            specialDiscountRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new BackendException("Special discount with given id doesn't exist.", e);
        }
    }
}
