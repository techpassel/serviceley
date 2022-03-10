package com.tp.serviceley.server.controller.user;

import com.tp.serviceley.server.dto.AddressRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "/address")
    public ResponseEntity<?> createAddress(@RequestBody AddressRequestDto addressRequestDto){
        try{
            return new ResponseEntity<>(userService.createAddress(addressRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
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
}
