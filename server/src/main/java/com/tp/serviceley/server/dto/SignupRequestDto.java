package com.tp.serviceley.server.dto;

import com.tp.serviceley.server.model.UserType;
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

    public SignupRequestDto(String email, String firstName, String lastName, String password){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = UserType.User;
    }

    public SignupRequestDto(String email, String firstName, String lastName, String password, String userType){
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.userType = UserType.valueOf(userType.toLowerCase());
    }
}
