package com.tp.serviceley.server.model;

import com.tp.serviceley.server.model.enums.TokenType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "token")
public class VerificationToken extends CreateUpdateRecord{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String token;
    //We will use this field to store VerificationToken as well as OTP for phone verification.

    @NotEmpty
    @Column(name = "token_type")
    TokenType tokenType;

    // We will use updatingValue field to store email or phone number when user will try to update existing values
    // We will update these value in User table only after user verifies the token(or otp)
    @Column(name = "updating_value")
    String updatingValue;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}

