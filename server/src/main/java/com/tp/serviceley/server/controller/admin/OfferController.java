package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.CouponRequestDto;
import com.tp.serviceley.server.dto.OfferRequestDto;
import com.tp.serviceley.server.dto.SpecialDiscountRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.admin.OfferService;
import lombok.AllArgsConstructor;
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

    @RequestMapping(method = RequestMethod.POST, value = "/coupon")
    public ResponseEntity<?> createCoupon(@RequestBody CouponRequestDto couponRequestDto) {
        try {
            return new ResponseEntity<>(offerService.createCoupon(couponRequestDto), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/coupon/{id}")
    public ResponseEntity<?> deleteCoupon(@PathVariable Long id){
        try{
            offerService.deleteCoupon(id);
            return new ResponseEntity<>("Coupon deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/special-discount")
    public ResponseEntity<?> createSpecialDiscount(@RequestBody SpecialDiscountRequestDto specialDiscountRequestDto) {
        try {
            return new ResponseEntity<>(offerService.createSpecialDiscount(specialDiscountRequestDto), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/special-discount/{id}")
    public ResponseEntity<?> deleteSpecialDiscount(@PathVariable Long id){
        try{
            offerService.deleteSpecialDiscount(id);
            return new ResponseEntity<>("Special discount deleted successfully.", HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred.Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
