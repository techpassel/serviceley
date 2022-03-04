package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoUser;
import com.tp.serviceley.server.model.enums.Religion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderResponseDto {
    private Long id;
    private DtoUser user;
    private Integer age;
    private Religion religion;
    private List<String> languages;
    private String qualification;
    private String qualificationCertificate;

    private String image1;

    private String image2;

    private String image3;

    private String idProof;

    private String addressProof;
}
