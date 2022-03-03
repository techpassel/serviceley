package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.dto.UserSpecialDiscountRequestDto;
import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.admin.UserSpecialDiscountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/user-special-discount")
@AllArgsConstructor
public class UserSpecialDiscountController {
    private final UserSpecialDiscountService userSpecialDiscountService;

    @RequestMapping(method = RequestMethod.POST, value = "")
    public ResponseEntity<?> assignSpecialCoupon(@RequestBody UserSpecialDiscountRequestDto specialDiscountData) {
        try {
            return new ResponseEntity<>(userSpecialDiscountService.assignUserSpecialDiscount(specialDiscountData), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.PUT, value = "")
    public ResponseEntity<?> approveRejectSpecialDiscount(@RequestBody Map<String, Object> data) {
        try {
            /*
             In the request body Map we need 3 entries(i.e. key-value pairs) with keys as "id", "status" and "remark".
             Here "id" represents the 'id' of UserSpecialDiscount, "status" represents 'status' of
             UserSpecialDiscount(hence should be of type 'SpecialDiscountStatus'), and "remark" represents
             approvalRemark of UserSpecialDiscount
            */
            return new ResponseEntity<>(userSpecialDiscountService.approveRejectSpecialDiscount(data), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/:id")
    public ResponseEntity<?> deleteUserSpecialDiscount(@PathVariable Long id) {
        try {
            userSpecialDiscountService.deleteUserSpecialDiscount(id);
            return new ResponseEntity<>("Special discount removed for user successfully.", HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/:specialDiscountId")
    private ResponseEntity<?> getSpecialDiscountUsers(@PathVariable Long specialDiscountId) {
        try {
            return new ResponseEntity<>(userSpecialDiscountService.getSpecialDiscountUsers(specialDiscountId), HttpStatus.OK);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
