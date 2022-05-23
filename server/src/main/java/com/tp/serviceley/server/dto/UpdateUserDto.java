package com.tp.serviceley.server.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp.serviceley.server.model.enums.Gender;
import com.tp.serviceley.server.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Gender gender;
    private UserType userType;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dob;

    public UpdateUserDto(String email, String firstName, String lastName, String userType, Gender gender,
                         String phone, LocalDate dob) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.userType = userType != null ? UserType.valueOf(userType.toLowerCase()) : null;
        this.phone = phone;
        this.dob = dob;
    }
}