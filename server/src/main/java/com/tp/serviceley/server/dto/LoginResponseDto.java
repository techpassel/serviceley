package com.tp.serviceley.server.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userType;
    private String token;
    private Integer onboardingStage;
}