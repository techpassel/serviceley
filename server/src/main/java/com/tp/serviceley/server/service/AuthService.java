package com.tp.serviceley.server.service;

import com.tp.serviceley.server.dto.LoginRequestDto;
import com.tp.serviceley.server.dto.LoginResponseDto;
import com.tp.serviceley.server.dto.SignupRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.NotificationEmail;
import com.tp.serviceley.server.model.enums.TokenType;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.VerificationToken;
import com.tp.serviceley.server.repository.UserRepository;
import com.tp.serviceley.server.repository.VerificationTokenRepository;
import com.tp.serviceley.server.security.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;
    private final MailContentBuilder mailContentBuilder;
    private final MailService mailService;
    private final JwtProvider jwtProvider;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signup(SignupRequestDto signupRequestDto){
        String email = signupRequestDto.getEmail();
        Optional<User> emailUser = userRepository.findByEmail(email);
        if(emailUser.isPresent()){
            throw new BackendException("Email already exist");
        }

        User user = new User();
        user.setFirstName(signupRequestDto.getFirstName());
        user.setLastName(signupRequestDto.getLastName());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setEmail(email);
        user.setUserType(signupRequestDto.getUserType());
        user.setGender(signupRequestDto.getGender());
        userRepository.save(user);
        sendAccountActivationEmail(user);
    }

    public String resendActivationEmail(String email){
        Optional<User> emailUser = userRepository.findByEmail(email);
        if(emailUser.isPresent()){
            User user = emailUser.get();
            if (user.isActive() && user.isEmailVerified()){
                throw new BackendException("User Account is already activated");
            } else {
                sendAccountActivationEmail(user);
            }
            return "An activation email is sent successfully on your registered email";
        } else {
            throw new BackendException("User with given email not found");
        }
    }

    private void sendAccountActivationEmail(User user){
        String token = generateVerificationToken(user);
        String url = "http://localhost:8080/api/auth/account-verification/"+token;
        String btnName = "Activate";
        String text = "Thanks for signing up on serviceley. Please click on the button below to activate your account.";
        String msg = mailContentBuilder.build(text, url, btnName);

        String successResponse = "Activation email sent!";
        String subject = "Please Activate your account.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg, successResponse));
    }

    private String generateVerificationToken(User user){
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken();
        verificationToken.setToken(token);
        verificationToken.setTokenType(TokenType.AccountActivation);
        verificationToken.setUser(user);
        verificationToken.setCreatedAt(LocalDateTime.now());
        verificationTokenRepository.save(verificationToken);
        return token;
    }

    @Transactional
    public void verifyAccount(String token){
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new BackendException("Invalid token"));
        fetchUserAndEnable(verificationToken.get());
    }

    public void fetchUserAndEnable(VerificationToken verificationToken){
        Optional<User> tokenUser = userRepository.findById(verificationToken.getUser().getId());
        tokenUser.orElseThrow(() -> new BackendException("User not found.Please signup again."));
        User user = tokenUser.get();
        user.setActive(true);
        user.setEmailVerified(true);
        userRepository.save(user);
        verificationTokenRepository.deleteById(verificationToken.getId());
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(),
                loginRequestDto.getPassword()));
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDto.getEmail());
        final String jwt = jwtProvider.generateToken(userDetails);
        Optional<User> user = userRepository.findByEmail(loginRequestDto.getEmail());
        user.orElseThrow(() -> new BackendException("User not found."));
        User u = user.get();
        return new LoginResponseDto(u.getFirstName(), u.getLastName(), u.getEmail(), u.getUserType().getType(), jwt);
    }

}
