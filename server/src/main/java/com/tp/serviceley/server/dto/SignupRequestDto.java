package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.enums.Gender;
import com.tp.serviceley.server.model.enums.UserType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SignupRequestDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private UserType userType;
    private Gender gender;

    public SignupRequestDto(String email, String firstName, String lastName, String password, String gender){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = UserType.User;
        this.gender = Gender.valueOf(gender);
    }

    public SignupRequestDto(String email, String firstName, String lastName, String password, String userType, String gender){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = UserType.valueOf(userType.toLowerCase());
        this.gender = Gender.valueOf(gender);
    }
}
