package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.OfferRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.admin.OfferService;
import lombok.AllArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/offer")
@AllArgsConstructor
public class OfferController {
    private final OfferService offerService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> createOffer(@RequestBody OfferRequestDto offerRequestDto) {
        try {
            return new ResponseEntity<>(offerService.createOffer(offerRequestDto), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Long id){
        try{
            offerService.deleteOffer(id);
            return new ResponseEntity<>("Offer deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
