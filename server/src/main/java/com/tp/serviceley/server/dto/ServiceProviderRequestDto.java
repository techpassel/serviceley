package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.Religion;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceProviderRequestDto {
    private Long id;
    private Long userId;
    private Integer age;
    private Religion religion;
    private List<String> languages;
    private String qualification;
    private MultipartFile qualificationCertificate;

    private MultipartFile image1;

    private MultipartFile image2;

    private MultipartFile image3;

    private MultipartFile idProof;

    private MultipartFile addressProof;
}
