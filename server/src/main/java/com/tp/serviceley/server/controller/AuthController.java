package com.tp.serviceley.server.controller;

import com.tp.serviceley.server.dto.LoginRequestDto;
import com.tp.serviceley.server.dto.ResetPasswordRequestDto;
import com.tp.serviceley.server.dto.SignupRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.User;
import com.tp.serviceley.server.service.CommonService;
import com.tp.serviceley.server.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    // Constructor based dependency injection
    private final AuthService authService;
    private final CommonService commonService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody SignupRequestDto signupRequestDto){
        try {
            return new ResponseEntity<>(authService.signup(signupRequestDto), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ConstraintViolationException e){
            return new ResponseEntity<>(commonService.buildConstraintViolations(e), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
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
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (DisabledException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/resend-activation-email")
    public ResponseEntity<?> resendActivationEmail(@RequestBody String username) {
        try {
            return new ResponseEntity<>(authService.resendActivationEmail(username), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<?> createForgetPassword(@RequestBody Map<String, String> userdata){
        try{
            String email = userdata.get("email");
            return new ResponseEntity<>(authService.createForgetPassword(email), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/reset-password/{token}")
    public ResponseEntity<?> verifyResetPasswordToken(@PathVariable String token){
        try{
            User user= authService.verifyResetPasswordToken(token).getUser();
            return new ResponseEntity<>(user.getId(), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<?> resetToken(@RequestBody ResetPasswordRequestDto resetPasswordRequestDto){
        try{
            return new ResponseEntity<>(authService.resetPassword(resetPasswordRequestDto), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logout/{session}")
    public ResponseEntity<?> logout(@PathVariable String session){
        try{
            return new ResponseEntity<>(authService.logout(session), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_ACCEPTABLE);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}