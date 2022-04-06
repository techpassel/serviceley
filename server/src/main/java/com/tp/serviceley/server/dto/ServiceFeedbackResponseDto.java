package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.OrderItem;
import com.tp.serviceley.server.model.dto_related.DtoOrderItem;
import com.tp.serviceley.server.model.dto_related.DtoServiceProvider;
import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFeedbackResponseDto {
    private Long id;
    private DtoServiceSubtype serviceSubtype;
    private DtoServiceProvider serviceProvider;
    private DtoOrderItem orderItem;
    private String feedback;
    private Integer satisfactionRating;
}
