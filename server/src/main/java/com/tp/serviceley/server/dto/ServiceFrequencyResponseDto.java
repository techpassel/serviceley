package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoServiceSubtype;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFrequencyResponseDto {
    private Long id;
    private DtoServiceSubtype serviceSubtype;
    private String frequency;
    private String frequencyDetails;
}
