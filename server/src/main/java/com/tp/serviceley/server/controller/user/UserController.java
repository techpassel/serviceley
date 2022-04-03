package com.tp.serviceley.server.controller.user;

import com.tp.serviceley.server.dto.AddressRequestDto;
import com.tp.serviceley.server.dto.UpdateUserDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/address")
    public ResponseEntity<?> createAddress(@RequestBody AddressRequestDto addressRequestDto){
        try{
            return new ResponseEntity<>(userService.createAddress(addressRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/address/{id}")
    public ResponseEntity<?> deleteAddress(@PathVariable Long id){
        try{
            userService.deleteAddress(id);
            return new ResponseEntity<>("Address deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto updateUserDto){
        try{
            return new ResponseEntity<>(userService.updateUser(updateUserDto), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/phone")
    public ResponseEntity<?> sendMobileVerificationToken(@RequestBody Map<String, Long> data){
        try{
            Long userId = data.get("userId");
            Long phone = data.get("phone");
            return new ResponseEntity<>(userService.sendMobileVerificationToken(userId, phone), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/phone")
    public ResponseEntity<?> verifyMobileVerificationToken(@RequestBody Map<String, Long> data){
        try{
            Long userId = data.get("userId");
            Long otp = data.get("otp");
            return new ResponseEntity<>(userService.verifyMobileVerificationToken(userId, otp), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/email")
    public ResponseEntity<?> sendUpdateEmailVerificationToken(@RequestBody Map<String, Object> data){
        try{
            Long userId = (Long) data.get("userId");
            String email = (String) data.get("email");
            return new ResponseEntity<>(userService.sendUpdateEmailVerificationToken(userId, email), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/email/{id}")
    public ResponseEntity<?> verifyUpdateEmailVerificationToken(@PathVariable String token){
        try{
            return new ResponseEntity<>(userService.verifyUpdateEmailVerificationToken(token), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/deactivate")
    public ResponseEntity<?> deactivateUser(@RequestBody Map<String, Object> data){
        try{
            Long userId = (Long) data.get("userId");
            String reason = (String) data.get("reason");
            return new ResponseEntity<>(userService.deactivateUser(userId, reason), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
