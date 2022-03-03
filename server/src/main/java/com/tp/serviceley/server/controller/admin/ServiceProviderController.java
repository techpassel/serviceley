package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.ServiceProviderRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.admin.ServiceProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/service-provider")
@AllArgsConstructor
public class ServiceProviderController {
    private ServiceProviderService serviceProviderService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> createServiceProvider(@RequestBody ServiceProviderRequestDto serviceProviderRequestDto){
        try{
            return new ResponseEntity<>(serviceProviderService.createServiceProvider(serviceProviderRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteServiceProvider(@PathVariable Long id){
        try{
            serviceProviderService.deleteServiceProvider(id);
            return new ResponseEntity<>("Service provider deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
