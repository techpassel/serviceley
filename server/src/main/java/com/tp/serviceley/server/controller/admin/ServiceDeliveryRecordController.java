package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.ServiceDeliveryRecordRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.admin.ServiceDeliveryRecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/admin/service-record")
public class ServiceDeliveryRecordController {
    private final ServiceDeliveryRecordService serviceDeliveryRecordService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> createServiceDeliveryRecord(@RequestBody ServiceDeliveryRecordRequestDto
                                                                     serviceDeliveryRecordRequestDto){
        try {
            return new ResponseEntity<>(serviceDeliveryRecordService.createServiceDeliveryRecord
                    (serviceDeliveryRecordRequestDto), HttpStatus.CREATED);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteServiceDeliveryRecord(@PathVariable Long id){
        try {
            serviceDeliveryRecordService.deleteServiceDeliveryRecord(id);
            return new ResponseEntity<>("Service delivery record deleted successfully.", HttpStatus.CREATED);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
