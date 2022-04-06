package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoOrderItem;
import com.tp.serviceley.server.model.dto_related.DtoServiceProvider;
import com.tp.serviceley.server.model.dto_related.DtoStaff;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDeliverRecordResponseDto {
    private Long id;
    private DtoOrderItem orderItem;
    private LocalDate date;
    private String slotTime;
    private DtoServiceProvider serviceProvider;
    private boolean isServiceDelivered;
    private String serviceProviderRemark;
    private String customerRemark;
    private Integer customerSatisfactionRating;
    private String staffRemark;
    private boolean isMarkedAsIgnored;
    private DtoStaff staff;
}
