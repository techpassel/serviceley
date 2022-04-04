package com.tp.serviceley.server.model.dto_related;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoServiceFrequency {
    private Long id;
    private String frequency;
    private String frequencyDetails;
}
