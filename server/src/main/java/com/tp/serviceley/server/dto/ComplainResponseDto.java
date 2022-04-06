package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.OrderItem;
import com.tp.serviceley.server.model.dto_related.*;
import com.tp.serviceley.server.model.enums.ComplainType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplainResponseDto {
    private Long id;
    private String displayComplainId;
    private DtoUser user;
    private ComplainType complainType;
    private ServiceProviderResponseDto serviceProvider;
    private DtoOrder order;
    private OrderBillingResponseDto orderBilling;
    private OrderItemResponseDto orderItem;
    private LocalDate fromDate;
    private LocalDate uptoDate;
    private String finalRemark;
    private DtoStaff finalRemarkBy;
    private Integer customerSatisfactionRating;
    private String customerReview;
    private List<ComplainMessageResponseDto> messages;
}
