package com.tp.serviceley.server.controller.user;

import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.user.UserServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/service")
public class UserServiceController {
    private final UserServiceService userServiceService;

    @RequestMapping(method = RequestMethod.GET, value = "/types")
    public ResponseEntity<?> getServiceTypes(){
        try {
            return new ResponseEntity<>(userServiceService.getServiceTypes(), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subtypes/{typeId}")
    public ResponseEntity<?> getServiceSubtypes(@PathVariable Long typeId){
        try {
            return new ResponseEntity<>(userServiceService.getServiceSubtypes(typeId), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/subtype/{id}")
    public ResponseEntity<?> getServiceSubtypeDetails(@PathVariable Long id){
        try {
            return new ResponseEntity<>(userServiceService.getServiceSubtypeDetails(id), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/service-unit/{subtypeId}")
    public ResponseEntity<?> getServiceUnits(@PathVariable Long subtypeId){
        try {
            return new ResponseEntity<>(userServiceService.getServiceUnits(subtypeId), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/service-unit/{id}")
    public ResponseEntity<?> getServiceUnitDetails(@PathVariable Long id){
        try {
            return new ResponseEntity<>(userServiceService.getServiceUnitDetails(id), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/service-frequency/{subtypeId}")
    public ResponseEntity<?> getServiceFrequencies(@PathVariable Long subtypeId){
        try {
            return new ResponseEntity<>(userServiceService.getServiceFrequencies(subtypeId), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
