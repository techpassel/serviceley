package com.tp.serviceley.server.controller;

import com.tp.serviceley.server.dto.ServiceSubtypeRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/service")
@AllArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> createServiceType(@RequestBody ServiceType service){
        try {
            HttpStatus status = service.getId() == null?HttpStatus.CREATED:HttpStatus.OK;
            return new ResponseEntity<>(serviceService.createServiceType(service), status);
        } catch (DataIntegrityViolationException e){
            //Will be thrown in case on duplicate entry for unique type fields(i.e. service type in this case).
            return new ResponseEntity<>("Service type already exist.", HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteServiceType(@PathVariable Long id){
        try{
            serviceService.deleteServiceType(id);
            return new ResponseEntity<>("Service type deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/subtype")
    public ResponseEntity<?> createServiceSubtype(@RequestBody ServiceSubtypeRequestDto serviceSubtypeData){
        try{
            return new ResponseEntity<>(serviceService.createServiceSubtype(serviceSubtypeData), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/subtype/{id}")
    public ResponseEntity<?> deleteServiceSubtype(@PathVariable Long id){
        try{
            serviceService.deleteServiceSubtype(id);
            return new ResponseEntity<>("Service subtype deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
