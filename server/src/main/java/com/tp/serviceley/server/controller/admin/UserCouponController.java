package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.UserCouponMultipleUserRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.admin.UserCouponService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/user-coupon")
@AllArgsConstructor
public class UserCouponController {
    private final UserCouponService userCouponService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    private ResponseEntity<?> assignCouponToUser(@RequestBody UserCouponMultipleUserRequestDto userCouponsData){
        try {
            return new ResponseEntity<>(userCouponService.assignUserCoupon(userCouponsData), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/:id")
    private ResponseEntity<?> removeUserCoupon(@PathVariable Long id){
        try {
            userCouponService.deleteUserCoupon(id);
            return new ResponseEntity<>("User coupon deleted successfully.", HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/:couponId")
    private ResponseEntity<?> getCouponUsers(@PathVariable Long couponId){
        try {
            return new ResponseEntity<>(userCouponService.getCouponUsers(couponId), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
