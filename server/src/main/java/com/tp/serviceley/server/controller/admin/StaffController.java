package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.StaffRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.admin.StaffService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/staff")
@AllArgsConstructor
public class StaffController {
    private final StaffService staffService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> createStaff(@RequestBody StaffRequestDto staffRequestDto){
        try {
            return new ResponseEntity<>(staffService.createStaff(staffRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id){
        try{
            staffService.deleteStaff(id);
            return new ResponseEntity<>("Staff deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
