package com.tp.serviceley.server.controller;

import com.tp.serviceley.server.dto.LoginRequestDto;
import com.tp.serviceley.server.dto.SignupRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.enums.UserType;
import com.tp.serviceley.server.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    // Constructor based dependency injection
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto){
        try {
            if(signupRequestDto.getUserType() == null){
                signupRequestDto.setUserType(UserType.User);
            }
            authService.signup(signupRequestDto);
            return new ResponseEntity<>("User registered successfully.An activation email is sent " +
                    "successfully on your registered email.Please verify your email.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/account-verification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token){
        try {
            authService.verifyAccount(token);
            return new ResponseEntity<>("Account activated successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginRequestDto));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>("Incorrect email or password.", HttpStatus.UNAUTHORIZED);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (DisabledException e){
            return new ResponseEntity<>("User is disabled now.Please check if email is verified.", HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<?> resendActivationEmail(@RequestBody HashMap<String, String> data) {
        try {
            String email = data.get("email");
            return new ResponseEntity<>(authService.resendActivationEmail(email), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
