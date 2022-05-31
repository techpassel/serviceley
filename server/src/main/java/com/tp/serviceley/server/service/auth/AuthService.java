package com.tp.serviceley.server.service.auth;

import com.tp.serviceley.server.dto.LoginRequestDto;
import com.tp.serviceley.server.dto.LoginResponseDto;
import com.tp.serviceley.server.dto.ResetPasswordRequestDto;
import com.tp.serviceley.server.dto.SignupRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.NotificationEmail;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.model.VerificationToken;
import com.tp.serviceley.server.model.enums.TokenType;
import com.tp.serviceley.server.model.enums.UserType;
import com.tp.serviceley.server.model.redis.RedisSession;
import com.tp.serviceley.server.repository.UserRepository;
import com.tp.serviceley.server.repository.VerificationTokenRepository;
import com.tp.serviceley.server.util.JwtProvider;
import com.tp.serviceley.server.service.CommonService;
import com.tp.serviceley.server.service.MailContentBuilder;
import com.tp.serviceley.server.service.MailService;
import com.tp.serviceley.server.service.user.CartService;
import com.tp.serviceley.server.util.RedisUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private MailContentBuilder mailContentBuilder;
    @Autowired
    private MailService mailService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CommonService commonService;
    @Autowired
    private CartService cartService;
    @Autowired
    private RedisUtil redisUtil;

    @Value("${app.security.jwt.expiration.time}")
    private Long jwtExpirationInMillis;

    @Transactional
    public String signup(SignupRequestDto signupRequestDto) {
        String email = signupRequestDto.getEmail();
        String phone = signupRequestDto.getPhone();
        if (userRepository.findByEmail(email).isPresent()) {
            throw new BackendException("Email already exist");
        }
        if (userRepository.findByPhone(phone).isPresent()) {
            throw new BackendException("Phone already exist");
        }
        User user = new User();
        user.setFirstName(signupRequestDto.getFirstName());
        user.setLastName(signupRequestDto.getLastName());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setEmail(email);
        user.setPhone(phone);
        user.setUserType(signupRequestDto.getUserType() != null ? signupRequestDto.getUserType() : UserType.User);
        userRepository.save(user);
        sendAccountActivationEmail(user);
        return "User registered successfully. An activation email is sent on your registered email. " +
                "Please verify your email.";
    }

    public String resendActivationEmail(String username) {
        User user = null;
        if (commonService.isStringANumber(username)) {
            //It means user has provided phone number.
            user = userRepository.findByPhone(username).orElseThrow(() -> new BackendException(""));
        } else {
            //It means user has provided email.
            user = userRepository.findByEmail(username).orElseThrow(() -> new BackendException(""));
        }

        if (user.isActive() && user.isEmailVerified()) {
            throw new BackendException("User Account is already activated");
        } else {
            sendAccountActivationEmail(user);
        }
        return "An activation email is sent successfully on your registered email.Please verify your email.";

    }

    private void sendAccountActivationEmail(User user) {
        String token = commonService.generateVerificationToken(user, TokenType.AccountActivation);
        String url = "http://localhost:8080/api/auth/account-verification/" + token;
        String btnName = "Activate";
        String text = "Thanks for signing up on serviceley. Please click on the button below to activate your account.";
        String msg = mailContentBuilder.build(text, url, btnName);

        String subject = "Please Activate your account.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg));
    }

    @Transactional
    public void verifyAccount(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new BackendException("Invalid token"));
        VerificationToken verToken = verificationToken.get();
        if (isTokenExpired(verToken.getCreatedAt()) == false) {
            throw new BackendException("Token is expired.");
        }
        fetchUserAndEnable(verToken);
    }

    public boolean isTokenExpired(LocalDateTime createdAt) {
        //We will declare a token as expired if it was created before 72 hours
        return createdAt.plusHours(72).isAfter(LocalDateTime.now());
    }

    public void fetchUserAndEnable(VerificationToken verificationToken) {
        Optional<User> tokenUser = userRepository.findById(verificationToken.getUser().getId());
        tokenUser.orElseThrow(() -> new BackendException("User not found. Please signup again."));
        User user = tokenUser.get();
        user.setActive(true);
        user.setEmailVerified(true);
        userRepository.save(user);
        cartService.createCart(user);
        verificationTokenRepository.deleteById(verificationToken.getId());
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        String username = loginRequestDto.getUsername();
        User user = null;
        if (commonService.isStringANumber(username)) {
            //It means user has provided phone number.
            user = userRepository.findByPhone(username).orElseThrow(() -> new BackendException("InvalidPhoneException"));
        } else {
            //It means user has provided email.
            user = userRepository.findByEmail(username).orElseThrow(() -> new BackendException("InvalidEmailException"));
        }
        String email = user.getEmail();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,
                    loginRequestDto.getPassword()));
        } catch (DisabledException e) {
            if (user.isEmailVerified() == false) {
                throw new BackendException("EmailNotVerifiedException");
            } else {
                throw new DisabledException("InactiveUserException");
            }
        } catch (BadCredentialsException e) {
            // Since we have already checked if email exist or not above.
            // So throwing this error here simply means password is incorrect.
            throw new BackendException("InvalidPasswordException");
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        final String jwt = jwtProvider.generateToken(userDetails, user);
        String sessionKey = RandomStringUtils.randomAlphanumeric(24);
        RedisSession session = new RedisSession(user.getId(), user.getEmail(), jwt);
        redisUtil.set(sessionKey, session, jwtExpirationInMillis);
        return new LoginResponseDto(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(),
                user.getUserType().getType(), sessionKey);
    }

    public String createForgetPassword(String email) {
        Optional<User> emailUser = userRepository.findByEmail(email);
        if (emailUser.isEmpty()) {
            throw new BackendException("No user exist with given email. Please check your email or create " +
                    "a new account with this email.");
        }
        sendForgetPasswordEmail(emailUser.get());
        return "A link for resetting your password has sent on your registered email. Please follow the instruction.";
    }

    private void sendForgetPasswordEmail(User user) {
        String token = commonService.generateVerificationToken(user, TokenType.ResetPasswordVerification);
        String url = "http://localhost:8080/api/auth/reset-password/" + token;
        String btnName = "Reset";
        String text = "Please click on the button below to reset your password.";
        String msg = mailContentBuilder.build(text, url, btnName);
        String subject = "Reset your serviceley password.";
        String recipient = user.getEmail();
        mailService.sendMail(new NotificationEmail(subject, recipient, msg));
    }

    public VerificationToken verifyResetPasswordToken(String token) {
        Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
        verificationToken.orElseThrow(() -> new BackendException("Invalid token. Please try to reset your password again."));
        VerificationToken verToken = verificationToken.get();
        if (isTokenExpired(verToken.getCreatedAt()) == false) {
            verificationTokenRepository.deleteById(verToken.getId());
            throw new BackendException("Token is expired. Please try to reset your password again.");
        }
        return verToken;
    }

    public String resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
        if (!resetPasswordRequestDto.getPassword().equals(resetPasswordRequestDto.getConfirmPassword())) {
            throw new BackendException("Password and reset password did not match.");
        }
        VerificationToken verToken = verifyResetPasswordToken(resetPasswordRequestDto.getToken());
        User tokenUser = verToken.getUser();
        // Above method call can throw error. But we don't need to handle that here. It is being handled in controller.
        if (resetPasswordRequestDto.getUserId() == null || tokenUser.getId() != resetPasswordRequestDto.getUserId()) {
            throw new BackendException("This token is not valid for current user.");
        }
        tokenUser.setPassword(passwordEncoder.encode(resetPasswordRequestDto.getPassword()));
        userRepository.save(tokenUser);
        verificationTokenRepository.deleteById(verToken.getId());
        return "Password reset successfully";
    }
}
