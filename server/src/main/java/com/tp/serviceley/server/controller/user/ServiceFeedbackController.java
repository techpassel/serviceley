package com.tp.serviceley.server.controller.user;

import com.tp.serviceley.server.dto.ServiceFeedbackRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.user.ServiceFeedbackService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/service-feedback")
public class ServiceFeedbackController {
    private final ServiceFeedbackService serviceFeedbackService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> createServiceFeedback(ServiceFeedbackRequestDto serviceFeedbackRequestDto){
        try{
            return new ResponseEntity<>(serviceFeedbackService.createServiceFeedback(serviceFeedbackRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteServiceFeedback(@PathVariable Long id){
        try{
            serviceFeedbackService.deleteServiceFeedback(id);
            return new ResponseEntity<>("Service feedback deleted successfully.", HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
