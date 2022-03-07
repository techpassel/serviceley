package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.ProvidersEnrolledServiceRequestDto;
import com.tp.serviceley.server.dto.ServiceProviderFileDto;
import com.tp.serviceley.server.dto.ServiceProviderRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.admin.ServiceProviderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/service-provider")
@AllArgsConstructor
public class ServiceProviderController {
    private ServiceProviderService serviceProviderService;

    @RequestMapping(method = RequestMethod.POST, value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createServiceProvider(@ModelAttribute ServiceProviderRequestDto serviceProviderRequestDto){
        try{
            return new ResponseEntity<>(serviceProviderService.createServiceProvider(serviceProviderRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateServiceProviderFile(@ModelAttribute ServiceProviderFileDto fileKeyDto){
        try{
            return new ResponseEntity<>(serviceProviderService.updateServiceProviderFile(fileKeyDto), HttpStatus.CREATED);
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

    @RequestMapping(method = RequestMethod.POST, value = "/enroll")
    public ResponseEntity<?> createProvidersEnrolledService(@RequestBody ProvidersEnrolledServiceRequestDto
                                                               providersEnrolledServiceRequestDto){
        try{
            return new ResponseEntity<>(serviceProviderService.createProvidersEnrolledService
                    (providersEnrolledServiceRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/enroll/{id}")
    public ResponseEntity<?> deleteProvidersEnrolledService(@PathVariable Long id){
        try{
            serviceProviderService.deleteProvidersEnrolledService(id);
            return new ResponseEntity<>("Providers enrolled service deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
