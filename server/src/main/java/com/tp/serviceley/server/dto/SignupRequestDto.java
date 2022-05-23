package com.tp.serviceley.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp.serviceley.server.model.enums.Gender;
import com.tp.serviceley.server.model.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SignupRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserType userType;
    private String phone;


    public SignupRequestDto(String email, String firstName, String lastName, String password, String userType,
                            String phone) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = userType != null ? UserType.valueOf(userType.toLowerCase()) : null;
        this.phone = phone;
    }


}