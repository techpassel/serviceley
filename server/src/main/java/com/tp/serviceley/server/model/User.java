package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.Gender;
import com.tp.serviceley.server.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @Size(max = 50)
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "last_name")
    @Size(max = 50)
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(unique = true)
    @Size(max = 10, min = 10)
    private Integer phone;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email is required")
    @Column(unique = true)
    private String email;

    @NotEmpty(message = "Password is required")
    private String password;

    @NotNull
    private Gender gender;

    @Column(name="is_active")
    @Size(max = 1)
    private boolean isActive = false;

    @Column(name="is_phone_verified")
    @Size(max = 1)
    private boolean isPhoneVerified = false;

    @Column(name="is_email_verified")
    @Size(max = 1)
    private boolean isEmailVerified = false;

    @Column(name= "usertype")
    private UserType userType;
}
