package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDeliveryRecordRequestDto {
    private Long id;
    private Long orderItemId;
    private LocalDate date;
    private String slotTime;
    private Long serviceProviderId;
    private boolean isServiceDelivered;
    private String serviceProviderRemark;
    private String customerRemark;
    private Integer customerSatisfactionRating;
    private String staffRemark;
    private boolean isMarkedAsIgnored;
    private Long staffId;
}
