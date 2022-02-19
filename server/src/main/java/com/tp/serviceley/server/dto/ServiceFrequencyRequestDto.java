package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceFrequencyRequestDto {
    private Long id;
    private Long serviceSubtypeId;
    private String frequency;
    private String frequencyDetails;
}
