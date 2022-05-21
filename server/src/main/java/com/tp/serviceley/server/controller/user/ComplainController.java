package com.tp.serviceley.server.controller.user;

import com.tp.serviceley.server.dto.ComplainMessageRequestDto;
import com.tp.serviceley.server.dto.ComplainRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.user.ComplainService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/api/user/complain")
public class ComplainController {
    private final ComplainService complainService;
    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> createComplain(@RequestBody ComplainRequestDto complainRequestDto){
        try {
            return new ResponseEntity<>(complainService.createComplain(complainRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/message")
    public ResponseEntity<?> addMessageToComplain(@RequestBody ComplainMessageRequestDto complainMessageRequestDto){
        try {
            return new ResponseEntity<>(complainService.addMessageToComplain(complainMessageRequestDto), HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteComplain(@PathVariable Long id){
        try {
            complainService.deleteComplain(id);
            return new ResponseEntity<>("Complain deleted successfully.", HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/message/{id}")
    public ResponseEntity<?> deleteComplainMessage(@PathVariable Long id){
        try {
            complainService.deleteComplainMessage(id);
            return new ResponseEntity<>("Complain message deleted successfully.", HttpStatus.CREATED);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
