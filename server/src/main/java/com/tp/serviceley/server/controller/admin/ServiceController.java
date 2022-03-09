package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.ServiceFrequencyRequestDto;
import com.tp.serviceley.server.dto.ServiceSubtypeRequestDto;
import com.tp.serviceley.server.dto.ServiceUnitRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.service.admin.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/service")
@AllArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> createServiceType(@RequestBody ServiceType service){
        try {
            return new ResponseEntity<>(serviceService.createServiceType(service), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e){
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

    @RequestMapping(method = RequestMethod.POST, value = "/unit")
    public ResponseEntity<?> createServiceUnit(@RequestBody ServiceUnitRequestDto serviceUnitData){
        try {
            HttpStatus status = serviceUnitData.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;
            return new ResponseEntity<>(serviceService.createServiceUnit(serviceUnitData), status);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/unit/{id}")
    public ResponseEntity<?> deleteServiceUnit(@PathVariable Long id){
        try{
            serviceService.deleteServiceUnit(id);
            return new ResponseEntity<>("Service unit deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/frequency")
    public ResponseEntity<?> createServiceFrequency(@RequestBody ServiceFrequencyRequestDto serviceFrequencyRequestDto){
        try {
            HttpStatus status = serviceFrequencyRequestDto.getId() == null ? HttpStatus.CREATED : HttpStatus.OK;
            return new ResponseEntity<>(serviceService.createServiceFrequency(serviceFrequencyRequestDto), status);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/frequency/{id}")
    public ResponseEntity<?> deleteServiceFrequency(@PathVariable Long id){
        try{
            serviceService.deleteServiceFrequency(id);
            return new ResponseEntity<>("Service frequency deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
