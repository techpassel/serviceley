package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFeedbackRequestDto {
    private Long id;
    private Long serviceSubtypeId;
    private Long serviceProviderId;
    private Long orderItemId;
    private String feedback;
    private Integer satisfactionRating;
}
