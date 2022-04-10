package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.ComplainType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplainRequestDto {
    private Long id;
    private Long userId;
    private ComplainType complainType;
    private Long serviceProviderId;
    private Long orderId;
    private Long orderBillingId;
    private Long orderItemId;
    private LocalDate fromDate;
    private LocalDate uptoDate;
    private String finalRemark;
    private Long finalRemarkByStaffId;
    private Integer customerSatisfactionRating;
    private String customerReview;
    private ComplainMessageRequestDto firstMessage;
    // We will send
    // We will create a separate API to set nextResponseDeadline. So we are not including that here.
}
