package com.tp.serviceley.server.controller;

import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.model.ServiceType;
import com.tp.serviceley.server.service.ServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/service")
@AllArgsConstructor
public class ServiceController {
    private final ServiceService serviceService;

    @RequestMapping(method = RequestMethod.POST, value = "/")
    public ResponseEntity<?> addServiceType(@RequestBody Map<String, String> service){
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
}
