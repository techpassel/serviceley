package com.tp.serviceley.server.service;

import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.VerificationToken;
import com.tp.serviceley.server.model.enums.TokenType;
import com.tp.serviceley.server.repository.UserRepository;
import com.tp.serviceley.server.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommonService {
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userRepository.findByEmail(userName).orElseThrow(() -> new BackendException("Invalid User Details."));
        return user;
    }

    public String getFileExtension(String fileName) {
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return "." + fileName.substring(i + 1);
        } else {
            throw new BackendException("File doesn't have any extension.");
        }
    }

    public String generateVerificationToken(User user, TokenType tokenType) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setTokenType(tokenType);
        verificationToken.setUser(user);
        verificationToken.setCreatedAt(LocalDateTime.now());
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    public String generateDisplayOrderId() {
        String token = RandomStringUtils.randomAlphanumeric(8);
        return "SLEY-" + LocalDate.now() + "-" + token;
    }

    public String buildConstraintViolations(ConstraintViolationException e) {
        return e.getConstraintViolations().stream().
                map(v -> v.getConstraintDescriptor().getMessageTemplate())
                .collect(Collectors.joining(", "));
        //v.getMessage() also gives same result as v.getConstraintDescriptor().getMessageTemplate()
        //So v.getMessage() can also bve used.
        //Similarly [.reduce((f,s) -> f +", "+s).orElse(null);] can also be used in place of
        //[.collect(Collectors.joining(", "))]. Both will produce same result;
    }

    public boolean isStringANumber(String str) {
        String regex = "[0-9]+[\\.]?[0-9]*";
        return Pattern.matches(regex, str);
    }
}