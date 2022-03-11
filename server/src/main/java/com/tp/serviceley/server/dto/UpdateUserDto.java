package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.Gender;
import com.tp.serviceley.server.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Long phone;
    private String email;
    private Gender gender;
    private UserType userType;
}
