package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.dto_related.DtoUser;
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
    private String religion;
    private List<String> languages;
    private String qualification;
    private String qualification_certificate;

    private String image1;

    private String image2;

    private String image3;

    private String id_proof;

    private String address_proof;
}
