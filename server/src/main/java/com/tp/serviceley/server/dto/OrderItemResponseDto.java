package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.Offer;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.dto_related.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {
    private Long id;
    private Long orderId;
    private ServiceType serviceType;
    private DtoServiceSubtype serviceSubtype;
    private DtoServiceUnit serviceUnit;
    private DtoServiceFrequency serviceFrequency;
    private DtoOffer offer;
    private Long OfferDiscount;
    private LocalDateTime serviceFrom;
    private LocalDateTime serviceUpto;
    private DtoServiceProvider assignedServiceProvider;
    private DtoStaff assignedStaff;
    private String slotTime1;
    private String slotTime2;
    private String slotTime3;
    private String slotTime4;
    private String slotTime5;
}
