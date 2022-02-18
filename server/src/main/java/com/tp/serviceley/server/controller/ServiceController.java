package com.tp.serviceley.server.controller;

import com.tp.serviceley.server.dto.ServiceSubtypeRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.service.ServiceService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<?> createServiceType(@RequestBody Map<String, String> service){
        String serviceName = service.get("name");
        try {
            ServiceType serviceType = serviceService.createServiceType(serviceName);
            return new ResponseEntity<>(serviceType, HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "")
    public ResponseEntity<?> updateServiceType(@RequestBody Map<String, Object> serviceData){
        //We need to send "id" as String and not as Integer.
        Long id = Long.valueOf((String) serviceData.get("id"));
        String typeNewValue = (String) serviceData.get("typeNewValue");
        try {
            ServiceType serviceType = serviceService.updateServiceType(id, typeNewValue);
            return new ResponseEntity<>(serviceType, HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
}
