package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.Offer;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.model.dto_related.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
    private LocalDate serviceFromDate;
    private LocalDate serviceUptoDate;
    private DtoServiceProvider assignedServiceProvider;
    private DtoStaff assignedStaff;
    private String shiftTiming1;
    private String shiftTiming2;
    private String shiftTiming3;
}
