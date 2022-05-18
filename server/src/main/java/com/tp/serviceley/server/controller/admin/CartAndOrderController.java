package com.tp.serviceley.server.controller.admin;

import com.tp.serviceley.server.exception.BackendException;
import com.tp.serviceley.server.service.user.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/cart-order")
@AllArgsConstructor
public class CartAndOrderController {
    private final OrderService orderService;

    @RequestMapping(method = RequestMethod.PUT, value="/cancel")
    public ResponseEntity<?> cancelOrder(@RequestBody Map<String, Object> data){
        try{
            return new ResponseEntity<>(orderService.cancelOrder(data), HttpStatus.OK);
        } catch (BackendException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e){
            return new ResponseEntity<>("Some error occurred. Please try again.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
