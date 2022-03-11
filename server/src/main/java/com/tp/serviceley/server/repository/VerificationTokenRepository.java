package com.tp.serviceley.server.repository;

import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.VerificationToken;
import com.tp.serviceley.server.model.enums.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    Optional<VerificationToken> findByToken(String token);

    @Query("from VerificationToken where user=?1 and tokenType=?2")
    Optional<VerificationToken> findByUserAndTokenType(User user, TokenType tokenType);
}
