package com.tp.serviceley.server.service.auth;

import com.tp.serviceley.server.dto.LoginRequestDto;
import com.tp.serviceley.server.dto.LoginResponseDto;
import com.tp.serviceley.server.dto.ResetPasswordRequestDto;
import com.tp.serviceley.server.dto.SignupRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.NotificationEmail;
import com.tp.serviceley.server.model.enums.TokenType;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.VerificationToken;
import com.tp.serviceley.server.repository.UserRepository;
import com.tp.serviceley.server.repository.VerificationTokenRepository;
import com.tp.serviceley.server.security.JwtProvider;
import com.tp.serviceley.server.service.CommonService;
import com.tp.serviceley.server.service.MailContentBuilder;
import com.tp.serviceley.server.service.MailService;
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
    private final CommonService commonService;

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
            return "An activation email is sent successfully on your registered email.Please verify your email.";
        } else {
            throw new BackendException("User with given email not found");
        }
    }

    private void sendAccountActivationEmail(User user){
        String token = commonService.generateVerificationToken(user, TokenType.AccountActivation);
        String url = "http://localhost:8080/api/auth/account-verification/"+token;
        String btnName = "Activate";
        String text = "Thanks for signing up on serviceley. Please click on the button below to activate your account.";
        String msg = mailContentBuilder.build(text, url, btnName);

        String subject = "Please Activate your account.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg));
    }

    @Transactional
    public void verifyAccount(String token){
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new BackendException("Invalid token"));
        VerificationToken verToken = verificationToken.get();
        if(isTokenExpired(verToken.getCreatedAt()) == false){
            throw new BackendException("Token is expired.");
        }
        fetchUserAndEnable(verToken);
    }

    public boolean isTokenExpired(LocalDateTime createdAt){
        //We will declare a token as expired if it was created before 72 hours
        return createdAt.plusHours(72).isAfter(LocalDateTime.now());
    }

    public void fetchUserAndEnable(VerificationToken verificationToken){
        Optional<User> tokenUser = userRepository.findById(verificationToken.getUser().getId());
        tokenUser.orElseThrow(() -> new BackendException("User not found. Please signup again."));
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
        return new LoginResponseDto(u.getId(), u.getFirstName(), u.getLastName(), u.getEmail(), u.getUserType().getType(), jwt);
    }

    public String createForgetPassword(String email){
        Optional<User> emailUser = userRepository.findByEmail(email);
        if(emailUser.isEmpty()){
            throw new BackendException("No user exist with given email. Please check your email or create " +
                    "a new account with this email.");
        }
        sendForgetPasswordEmail(emailUser.get());
        return "A link for resetting your password has sent on your registered email. Please follow the instruction.";
    }

    private void sendForgetPasswordEmail(User user){
        String token = commonService.generateVerificationToken(user, TokenType.ResetPasswordVerification);
        String url = "http://localhost:8080/api/auth/reset-password/"+token;
        String btnName = "Reset";
        String text = "Please click on the button below to reset your password.";
        String msg = mailContentBuilder.build(text, url, btnName);
        String subject = "Reset your serviceley password.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg));
    }

    public VerificationToken verifyResetPasswordToken(String token){
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new BackendException("Invalid token. Please try to reset your password again."));
        VerificationToken verToken = verificationToken.get();
        if(isTokenExpired(verToken.getCreatedAt()) == false){
            verificationTokenRepository.deleteById(verToken.getId());
            throw new BackendException("Token is expired. Please try to reset your password again.");
        }
        return verToken;
    }

    public String resetPassword(ResetPasswordRequestDto resetPasswordRequestDto){
        if(!resetPasswordRequestDto.getPassword().equals(resetPasswordRequestDto.getConfirmPassword())){
            throw new BackendException("Password and reset password did not match.");
        }
        VerificationToken verToken = verifyResetPasswordToken(resetPasswordRequestDto.getToken());
        User tokenUser = verToken.getUser();
        // Above method call can throw error. But we don't need to handle that here. It is being handled in controller.
        if(resetPasswordRequestDto.getUserId() == null || tokenUser.getId() != resetPasswordRequestDto.getUserId()){
            throw new BackendException("This token is not valid for current user.");
        }
        tokenUser.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getPassword()));
        userRepository.save(tokenUser);
        verificationTokenRepository.deleteById(verToken.getId());
        return "Password reset successfully";
    }
}
